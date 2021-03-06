package es.uji.security.crypto.pdf;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import es.uji.security.crypto.test.BaseCryptoAppletTest;
import es.uji.security.crypto.ISignFormatProvider;
import es.uji.security.crypto.SignatureResult;
import es.uji.security.crypto.VerificationResult;
import es.uji.security.crypto.config.OS;

public class PDFTest extends BaseCryptoAppletTest
{
    @Before
    public void init() throws IOException
    {
        data = OS.inputStreamToByteArray(new FileInputStream("src/test/resources/in-pdf.pdf"));
        signatureOptions.setDataToSign(new ByteArrayInputStream(data));
        
        Map<String, String> bindValues = new HashMap<String, String>();
        bindValues.put("%x", "3439-2134-1371-0998");
        
        signatureOptions.setVisibleSignatureTextBindValues(bindValues);
        
        signatureOptions.setVisibleAreaTextPattern("%s con referencia %x a las %t");
        signatureOptions.setVisibleAreaRepeatAxis("Y");
    }

    @Test
    public void pdf() throws Exception
    {
        // Sign

        ISignFormatProvider signFormatProvider = new PDFSignatureFactory();
        SignatureResult signatureResult = signFormatProvider.formatSignature(signatureOptions);

        showErrors(signatureResult, "target/out-pdf.pdf");

        data = OS.inputStreamToByteArray(new FileInputStream("target/out-pdf.pdf"));
        signatureOptions.setDataToSign(new ByteArrayInputStream(data));
        
        signFormatProvider = new PDFSignatureFactory();
        signatureResult = signFormatProvider.formatSignature(signatureOptions);

        showErrors(signatureResult, "target/out-pdf2.pdf");

        // Verify

        byte[] signedData = OS.inputStreamToByteArray(new FileInputStream("target/out-pdf2.pdf"));

        PDFSignatureVerifier signatureVerifier = new PDFSignatureVerifier();
        VerificationResult verificationResult = signatureVerifier.verify(signedData);

        showErrors(verificationResult);
    }
}