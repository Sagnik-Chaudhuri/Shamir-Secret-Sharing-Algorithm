/**
 * Implementation of GF(256) with primitive, irreducible polynomial 285 (0x11D)
 * and minimum, primitive element (generator) = 2 (0x02)
 * 
 *
 */
public final class GaloisFieldTableGenerator {

	byte[] log = new byte[256];

	byte[] exp = new byte[1024];

	public GaloisFieldTableGenerator() {
		createTable();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Function to create the table
	 */
	void createTable() {
		byte exp[] = new byte[1024];
		int arr[] = new int[256];
		int brr[] = new int[256];
		byte log[] = new byte[256];
		brr[0] = 1;
		log[0] = (byte) 512;
		arr[0] = 1;
		exp[0] = (byte) 1;
		for (int i = 1; i < 256; i++) {
			arr[i] = arr[i - 1] * 2;
			if (arr[i] >= 256)
				arr[i] = arr[i] ^ 285;
			exp[i] = (byte) arr[i];
			log[exp[i] & 255] = (byte) i;
		}

		exp[255] = exp[0];
		log[exp[255] & 255] = (byte) 255;

		for (int i = 256; i < 510; i++) {
			exp[i] = exp[i % 255];
		}
		exp[510] = 1;
		for (int i = 511; i < 1020; i++)
			exp[i] = 0;
		this.exp = exp;
		this.log = log;
	}
}

