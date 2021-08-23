package com.redbird.rtback.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class MockupService {

    public MockupService() throws MalformedURLException {
    }

    public String getLaptopMockup(String img) {
        BufferedImage image = decodeToImg(img);
        BufferedImage output = overlayImages(laptopImg, image, 185, 50);
        log.info("laptop mockup is ready");
//        renderImage(output, "Laptop mockup");
        return encodeToBase64(output);
    }

    public String getIphoneMockup(String img) {
        BufferedImage image = decodeToImg(img);
        BufferedImage output = overlayImages(iphoneImg, image, 43, 43);
        output = overlayImages(output, iphoneImg, 0, 0);
        log.info("phone mockup is ready");
//        renderImage(output, "Iphone mockup");
        return encodeToBase64(output);
    }

    public static BufferedImage overlayImages(BufferedImage bgImage, BufferedImage fgImage, Integer x, Integer y) {
        BufferedImage buff = cloneImage(bgImage);
        Graphics2D g = buff.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(buff, 0, 0, null);
        g.drawImage(fgImage, x, y, null);

        g.dispose();
        return buff;
    }

    public static BufferedImage cloneImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

//    public void renderImage(BufferedImage image, String name) {
//        try {
//            File output = new File(name + ".png");
//            boolean flag = ImageIO.write(image, "png", output);
//            log.info("Image is ready");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }

    public BufferedImage decodeToImg(String img) {
        try {
            byte[] bytes = DatatypeConverter.parseBase64Binary(img);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
            return image;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    public String encodeToBase64(BufferedImage buff) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(buff, "png", output);
            return DatatypeConverter.printBase64Binary(output.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @PostConstruct
    private void init() {
        try {
            laptopImg = ImageIO.read(new File("mockup/desktop.png"));
            iphoneImg = ImageIO.read(new File("mockup/phone.png"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    URL laptopUrl = new URL("https://psv4.userapi.com/c536132/u156573721/docs/d10/c7c06a06c8de/desktop.png?extra=yXcpnxY0XchX-C2aAhn6j9Owsp_Lu3j-_cj5YIQgs9VnZGygEYy8Cg_LUeMI22eDQaO3Ek1TrqBha1J1znEEjvzmpmix1E0A0oH4YSxX-fC7HMuBbUYUgnOn-nHL5dExgaMhQd7MriELMQuC49ouaQ");

    URL iphoneUrl = new URL("https://psv4.userapi.com/c536132/u156573721/docs/d6/711914e5a7df/phone.png?extra=A-4Aor9QeZdErBaK3FejtF3dpFVdWyUlD0k8USbNW3LDii-x8Hs7BcXaSlS8OKD9GUjhjyPXsrpXtLsq26TCmn6IFXeX9v_NzyqUmxi72BLLdyq8EAk2XK3huSWpJt00eQV6nRD95oCEoafXFQFhHg");

    BufferedImage laptopImg;
    BufferedImage iphoneImg;

}
