package com.sagnik.shamir;
import static java.lang.Byte.toUnsignedInt;

import java.security.SecureRandom;
class GaloisField {
	

	private static final byte[] LOG = new GaloisFieldTableGenerator().log;
	
	private static final byte[] EXP = new GaloisFieldTableGenerator().exp;

	static byte add(byte a, byte b) {
		return (byte) (a ^ b);
	}

	static byte sub(byte a, byte b) {
		return add(a, b);
	}

	static byte mul(byte a, byte b) {
		if (a == 0 || b == 0) {
			return 0;
		}
		return EXP[toUnsignedInt(LOG[toUnsignedInt(a)]) + toUnsignedInt(LOG[toUnsignedInt(b)])];
	}

	static byte div(byte a, byte b) {
		// multiply by the inverse of b
		return mul(a, EXP[255 - toUnsignedInt(LOG[toUnsignedInt(b)])]);
	}

	static byte eval(byte[] p, byte x) {
		// Horner's method
		byte result = 0;
		for (int i = p.length - 1; i >= 0; i--) {
			result = add(mul(result, x), p[i]);
		}
		return result;
	}

	static int degree(byte[] p) {
		for (int i = p.length - 1; i >= 1; i--) {
			if (p[i] != 0) {
				return i;
			}
		}
		return 0;
	}

	static byte[] generate(SecureRandom random, int degree, byte x) {
		final byte[] p = new byte[degree + 1];

		// generate random polynomials until we find one of the given degree
		do {
			random.nextBytes(p);
		} while (degree(p) != degree);

		// set y intercept
		p[0] = x;

		return p;
	}

	static byte interpolate(byte[][] points) {
		// calculate f(0) of the given points using Lagrangian interpolation
		final byte x = 0;
		byte y = 0;
		for (int i = 0; i < points.length; i++) {
			final byte aX = points[i][0];
			final byte aY = points[i][1];
			byte li = 1;
			for (int j = 0; j < points.length; j++) {
				final byte bX = points[j][0];
				if (i != j) {
					li = mul(li, div(sub(x, bX), sub(aX, bX)));
				}
			}
			y = add(y, mul(li, aY));
		}
		return y;
	}
}
