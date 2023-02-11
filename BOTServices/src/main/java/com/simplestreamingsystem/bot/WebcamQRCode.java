package com.simplestreamingsystem.bot;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class WebcamQRCode extends JFrame implements Runnable, ThreadFactory {
    private Executor _executor = Executors.newSingleThreadExecutor(this);
    private Webcam _webcam = null;
    private String _qrResult = null;

    private WebcamPanel panel = null;
    private JTextArea textarea = null;
    public WebcamQRCode() {
        super();

        setLayout(new FlowLayout());
        setTitle("Read QR / Bar Code With Webcam");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension size = WebcamResolution.QVGA.getSize();
        //TODO: God save this mtfk code
        try {
            _webcam = Webcam.getWebcams().get(0);
        } catch (Exception e) {
            _webcam = Webcam.getWebcams().get(0);
        }

        _webcam.setViewSize(size);

        panel = new WebcamPanel(_webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        textarea = new JTextArea();
        textarea.setEditable(false);
        textarea.setPreferredSize(size);

        add(panel);
        add(textarea);

        //CONCERN: God please tell us what this mtfk do??
        pack();
        setVisible(true);

        _executor.execute(this);
    }

    public String getQRResult() {
        return _qrResult;
    }

    public void setQRResult(String value) {
        _qrResult = value;
    }

    @Override
    public void run() {
        do {
            Result result = null;
            BufferedImage image = null;

            if (_webcam.isOpen()) {

                if ((image = _webcam.getImage()) == null) {
                    continue;
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    assert false: "No QRCode";
                    // fall thru, it means there is no QR code in image
                }
            }

            if (result != null) {
                if (!result.getText().equals(getQRResult())) {
                    setQRResult(result.getText());
                    textarea.setText(result.getText());
                } else {
                    setQRResult(null);
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r, "webcam-runner");
        t.setDaemon(true);
        return t;
    }
}
