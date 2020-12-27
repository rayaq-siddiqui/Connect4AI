package connectFour;

import java.util.Arrays;

public class WeightTester {
	public static void main(String[] args) {
		// make this test the weight essentially
		// change the values of add and subtract of NewConnectRayaqAI
		// change one value at a time by .05 and see its influence

		double[] highest = { 0, 0, 0 };
		int totalWins = 0;

		for (double i = 0.5; i < 1.; i += .05) {
			for (double j = 1; j < 2.; j += .05) {
				for (double k = 1.5; k < 2.5; k += .05) {
					NewConnectRayaqAI.subtract[0] = i;
					NewConnectRayaqAI.subtract[1] = j;
					NewConnectRayaqAI.subtract[2] = k;

					try {
						Game.main(null);
					} catch (InterruptedException e) {
						System.out.println("error");
					}

					if (Game.player2Wins > totalWins) {
						totalWins = Game.player2Wins;
						highest[0] = i;
						highest[1] = j;
						highest[2] = k;
					}
					System.out.println(Arrays.toString(NewConnectRayaqAI.subtract));
				}
			}
		}

		System.out.println(Arrays.toString(highest));
		System.out.println(totalWins);
	}
}
