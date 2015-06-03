# CryptoApplet #

CryptoApplet is a Java applet for advanced digital signature creation developed by the [Universitat Jaume I](http://www.uji.es). The procedure is very simple: given a data input and a configuration determined by the server, the web client will create a digital signature of the data. The representation of the signature is obtained in the format specified in the configuration.

![Screenshot](https://github.com/universitatjaumei/cryptoapplet/raw/master/logo.png)

The signature representation formats supported by CryptoApplet are the following:

* Raw signature.
* CMS/PKCS#7.
* XML: from XML Signature to XAdES-X-L.
* PDF: PAdES signature with timestamp.
* ODT: XML Signature.

Certificate management is done transparently for the user through direct access to CryptoAPI, if we are using Microsoft Internet Explorer, or to PKCS#11 if we are using Firefox (either in Windows or GNU/Linux). Stored certificates can also be used if the client system has the Clauer software installed.

The applet can also be executed in Windows, GNU/Linux or MacOSX, the only requirement is to have a Sun's Java Virtual Machine (version 1.6 o higher) installed.

You can find more information in the [official documentation](http://jira.uji.es/confluence/display/CRYPTOAPPLET/) or posting your questions in our [public mailing list at Google Groups](https://groups.google.com/a/uji.es/group/cryptoapplet?lnk=)

## Changes it this fork over the original UJI's CryptoApplet (as of 06/02/2015) ##

* Support for IcedTea-Web plugin on Linux.
* Cookies usage completely disabled to avoid problems around session cookies set by the server with the `HttpOnly` attribute.
* A Maven's module (`uji-dist`) has been created to simplify the releasing and signing process.
* Added support for 64-bit Chrome and Internet Explorer on Windows.
* New configuration parameter to allow to sign with any certificate, not just the certificates with a CA configured in `ujiCrypto.conf`.
* Supporting certificates without a CN (common name) in the DN.
* Additional fixes.

## How to generate the signed applet distribution ##
Simply:

    mvn clean install

Or if you have a commercial code signing certificate (you can buy one at https://author.tucows.com/ for $70):

    mvn clean install -Duji.keytool.alias="entry alias" -Duji.keytool.keystore="/path/to/codesigning_certificate.jks" -Duji.keytool.password="secret"

Then go to `uji-dist/target/signed-applet` directory and there will be all the signed artifacts (including dependencies) that you need to copy to your project (currently it is generating a distribution for PDF signature only).

## Warning about applets##
*Read this before using CryptoApplet (or this fork) for a new project.*

Browsers support for applets is being deprecated (as of 06/2015), see https://java.com/en/download/faq/chrome.xml.

For that reason, I would suggest not to use this applet (or any other applet) for new projects, take a look at "Additional Deployment Options" in https://blogs.oracle.com/java-platform-group/entry/java_web_start_in_or.