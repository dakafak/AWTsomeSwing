package dev.fanger.awtsomeswing.tools;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsTool {

    public static Image loadImage(String imageUrl) throws IOException {
        return ImageIO.read(new File(imageUrl));
    }

    public static BufferedImage getBufferedImageFromFile(String filePath, int imageType) throws IOException {
        Image imageFile = loadImage(filePath);

        BufferedImage bufferedImage = new BufferedImage(imageFile.getWidth(null), imageFile.getHeight(null), imageType);
        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.drawImage(imageFile, 0, 0, imageFile.getWidth(null), imageFile.getHeight(null), null);

        return bufferedImage;
    }

    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        // Flip the image horizontally
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    public static BufferedImage rotateImage(BufferedImage image, double degrees) {
        double radianAngle = Math.toRadians(degrees);

        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D imageGraphics = rotatedImage.createGraphics();
        AffineTransform tx = AffineTransform.getRotateInstance(-radianAngle, image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        imageGraphics.drawImage(op.filter(image, null), 0, 0, null);

        return rotatedImage;
    }

}
