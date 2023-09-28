package helps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFix {

	// Rotate

	public static BufferedImage getRotatedImage(BufferedImage img, int rotAngle) {
		int width = img.getWidth();
		int height = img.getHeight();

		BufferedImage newImage = new BufferedImage(width, height, img.getType());
		Graphics2D graphics2d = newImage.createGraphics();

		graphics2d.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
		graphics2d.drawImage(img, 0, 0, null);
		graphics2d.dispose();

		return newImage;
	}

	// Image Layer Build

	public static BufferedImage buildImage(BufferedImage[] imgs) {
		int width = imgs[0].getWidth();
		int height = imgs[0].getHeight();

		BufferedImage newImage = new BufferedImage(width, height, imgs[0].getType());
		Graphics2D graphics2d = newImage.createGraphics();

		for (BufferedImage img : imgs) {
			graphics2d.drawImage(img, 0, 0, null);
		}

		graphics2d.dispose();
		return newImage;
	}

	// Rotate second image only
	public static BufferedImage getBuildRotImage(BufferedImage[] imgs, int rotAngle, int rotAtIndex) {

		int width = imgs[0].getWidth();
		int height = imgs[0].getHeight();

		BufferedImage newImage = new BufferedImage(width, height, imgs[0].getType());
		Graphics2D graphics2d = newImage.createGraphics();

		for (int i = 0; i < imgs.length; i++) {

			// Rotation of the first image, rotating back so second image stays in place
			if (rotAtIndex == i)
				graphics2d.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
			graphics2d.drawImage(imgs[i], 0, 0, null);
			if (rotAtIndex == i)
				graphics2d.rotate(Math.toRadians(-rotAngle), width / 2, height / 2);

		}

		graphics2d.dispose();
		return newImage;

	}

	// Rotate second image only + Animation
	public static BufferedImage[] getBuildRotImage(BufferedImage[] imgs, BufferedImage secondImg, int rotAngle) {

		int width = imgs[0].getWidth();
		int height = imgs[0].getHeight();

		BufferedImage[] arr = new BufferedImage[imgs.length];

		for (int i = 0; i < imgs.length; i++) {
			BufferedImage newImage = new BufferedImage(width, height, imgs[0].getType());
			Graphics2D graphics2d = newImage.createGraphics();

			graphics2d.drawImage(imgs[i], 0, 0, null);
			graphics2d.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
			graphics2d.drawImage(secondImg, 0, 0, null);
			graphics2d.dispose();

			arr[i] = newImage;
		}

		return arr;

	}

}
