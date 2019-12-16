import java.security.SecureRandom;
import java.util.Map;
import java.util.Scanner;

public class SSSImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		int n, k;
		System.out.println("Enter number of shares and k");
		n = sc.nextInt();
		k = sc.nextInt();
		final Scheme scheme = new Scheme(new SecureRandom(), n, k);
		System.out.println("Enter number of secrets");
		int m = sc.nextInt();
		for (int i = 0; i < m; i++) {
			System.out.println("Enter Secret");

			final byte[] secret = new byte[32];
			for (int i1 = 0; i1 < 32; i1++) {
				secret[i1] = sc.nextByte();
			}
			final Map<Integer, byte[]> parts = scheme.split(secret);
			final byte[] recovered = scheme.join(parts);

			System.out.println("Recovered Secret of length : " + recovered.length);
			for (int i1 = 0; i1 < 32; i1++)
				System.out.print(recovered[i1]);

		}
		sc.close();

	}

}

