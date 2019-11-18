package com.cisco.pmtpf.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.tomcat.util.codec.binary.Base64;

public class X509CertificateUtils {

	/**
	 * Converts a X509Certificate to pem format
	 *
	 * @param cert X509Certificate formatted String
	 * @return a String instance
	 * @throws CertificateEncodingException
	 */
	public static String convertToPem(X509Certificate cert) throws CertificateEncodingException {
		Base64 encoder = new Base64(64);
		String cert_begin = "-----BEGIN CERTIFICATE-----\n";
		String end_cert = "-----END CERTIFICATE-----";

		byte[] derCert = cert.getEncoded();
		String pemCertPre = new String(encoder.encode(derCert));
		String pemCert = cert_begin + pemCertPre + end_cert;

		return pemCert;
	}

	/**
	 * Converts a PEM formatted String to a {@link X509Certificate} instance.
	 *
	 * @param pem PEM formatted String
	 * @return a X509Certificate instance
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static X509Certificate convertToX509Certificate(InputStream is) throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
		return cert;
	}
}
