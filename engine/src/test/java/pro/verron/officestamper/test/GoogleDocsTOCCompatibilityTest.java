package pro.verron.officestamper.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.verron.officestamper.preset.OfficeStamperConfigurations.standardWithPreprocessing;

class GoogleDocsTocCompatibilityTest {
    @Test
    @DisplayName("Google Docs TOC compatibility test - regression test")
    void testGoogleDocsTocCompatibility() {
        var stamperConfiguration = standardWithPreprocessing();
        var stamper = new TestDocxStamper<>(stamperConfiguration);
        var templateStream = TestUtils.getResource(Path.of("GoogleDocsTOCCompatibility.docx"));

        var actual = stamper.stampAndLoadAndExtract(templateStream, null);
        var expected = """
                Hello, Bart!
                """;
        assertEquals(expected, actual);
    }
}
