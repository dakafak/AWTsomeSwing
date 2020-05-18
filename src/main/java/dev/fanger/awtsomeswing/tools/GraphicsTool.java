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

	private static String startDirectory = "images";

	// Fonts
	public static Font largeFont;

	// Images
	public static BufferedImage backgroundImage;

	static {
		// Load Fonts here
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			largeFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Cornerstone.ttf"));

			ge.registerFont(largeFont);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Load images here
		backgroundImage = getBufferedImageFromFile("main_large.png");
	}

	public static Image loadImage(String imageUrl){
		try {
			return ImageIO.read(new File(startDirectory + "/" + imageUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BufferedImage getBufferedImageFromFile(String filePath){
		Image imageFile = loadImage(filePath);

		BufferedImage bufferedImage = new BufferedImage(imageFile.getWidth(null), imageFile.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = bufferedImage.createGraphics();
		imageGraphics.drawImage(imageFile, 0, 0, imageFile.getWidth(null), imageFile.getHeight(null), null);

		return bufferedImage;
	}

	private BufferedImage flipImageHorizontally(BufferedImage image) {
		// Flip the image horizontally
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(image, null);
	}

	private BufferedImage rotateImage(BufferedImage image, double degrees) {
		double radianAngle = Math.toRadians(degrees);

		BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = rotatedImage.createGraphics();
		AffineTransform tx = AffineTransform.getRotateInstance(-radianAngle, image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		imageGraphics.drawImage(op.filter(image, null), 0, 0, null);

		imageGraphics.setFont(new Font("Monospaced", Font.BOLD, 72));
		imageGraphics.drawString(String.valueOf(degrees), image.getWidth() / 3, image.getHeight() / 2);

		return rotatedImage;
	}

}
