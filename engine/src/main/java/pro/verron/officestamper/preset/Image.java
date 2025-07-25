package pro.verron.officestamper.preset;

import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.R;
import pro.verron.officestamper.api.DocxPart;
import pro.verron.officestamper.api.OfficeStamperException;
import pro.verron.officestamper.utils.WmlFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class describes an image which will be inserted into a document.
 *
 * @author Joseph Verron
 * @author Romster
 * @version ${version}
 * @since 1.0.0
 */
public final class Image {

    private final byte[] imageBytes;
    private Integer maxWidth;

    /**
     * <p>Constructor for Image.</p>
     *
     * @param in - content of the image as InputStream
     *
     * @throws IOException if any.
     */
    public Image(InputStream in)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        this.imageBytes = out.toByteArray();
    }

    /**
     * <p>Constructor for Image.</p>
     *
     * @param in       - content of the image as InputStream
     * @param maxWidth - max width of the image in twip
     *
     * @throws IOException if any.
     */
    public Image(InputStream in, Integer maxWidth)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        this.imageBytes = out.toByteArray();
        this.maxWidth = maxWidth;
    }

    /**
     * <p>Constructor for Image.</p>
     *
     * @param imageBytes - content of the image as an array of the bytes
     */
    public Image(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    /**
     * <p>Constructor for Image.</p>
     *
     * @param imageBytes - content of the image as an array of the bytes
     * @param maxWidth   - max width of the image in twip
     */
    public Image(byte[] imageBytes, Integer maxWidth) {
        this.imageBytes = imageBytes;
        this.maxWidth = maxWidth;
    }

    /**
     * Creates a new run with the provided image and associated metadata.
     * <p>
     * TODO: adding the same image twice will put the image twice into the docx-zip file.
     *  We should make the second addition of the same image a reference instead.
     *
     * @param document     The document part where the image will be inserted.
     * @param filenameHint A hint for the filename to be used.
     * @param altText      Alternative text for the image.
     *
     * @return The created run containing the image.
     *
     * @throws OfficeStamperException If there is an error creating the image part
     */
    public R newRun(DocxPart document, String filenameHint, String altText) {
        WordprocessingMLPackage wordprocessingMLPackage = document.document();
        Part part = document.part();
        try {
            var image = BinaryPartAbstractImage.createImagePart(wordprocessingMLPackage, part, imageBytes);
            return WmlFactory.newRun(maxWidth, image, filenameHint, altText);
        } catch (Exception e) {
            throw new OfficeStamperException("Failed to create an ImagePart", e);
        }
    }

    /**
     * <p>Getter for the field <code>maxWidth</code>.</p>
     *
     * @return a {@link Integer} object
     *
     * @deprecated use the {@link #newRun(DocxPart, String, String)} method directly to generate a Run with Inline
     * Drawing
     */
    @Deprecated(since = "2.6", forRemoval = true) public Integer getMaxWidth() {
        return maxWidth;
    }

    /**
     * <p>Getter for the field <code>imageBytes</code>.</p>
     *
     * @return an array of byte objects
     *
     * @deprecated use the {@link #newRun(DocxPart, String, String)} method directly to generate a Run with Inline
     * Drawing
     */
    @Deprecated(since = "2.6", forRemoval = true) public byte[] getImageBytes() {
        return imageBytes;
    }
}
