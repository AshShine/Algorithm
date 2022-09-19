package SSWtest.SWEA.Unsolve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_5644_refactoring {

	static class User {
		int r, c;

		public User(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	static class BC {
		int r, c, coverage, performance;

		public BC(int r, int c, int coverage, int performance) {
			this.r = r;
			this.c = c;
			this.coverage = coverage;
			this.performance = performance;
		}

	}

	static int M, A;
	static User aUser, bUser;
	static BC[] bc;
	static int[] dr = { 0, -1, 0, 1, 0 };
	static int[] dc = { 0, 0, 1, 0, -1 };
	static int[] aPath, bPath; // a사용자경로, b사용자 경로

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken()); // 총 이동시간
			A = Integer.parseInt(st.nextToken()); // BC갯수

			aPath = new int[M + 1];
			bPath = new int[M + 1];

			// a사용자에 대한 경로, b사용자에 대한 경로
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++)
				aPath[i] = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++)
				bPath[i] = Integer.parseInt(st.nextToken());

			bc = new BC[A];
			for (int i = 0; i < A; i++) {
				st = new StringTokenizer(br.readLine());
				int inputX = Integer.parseInt(st.nextToken());
				int inputY = Integer.parseInt(st.nextToken());
				int inputCoverage = Integer.parseInt(st.nextToken());
				int inputPerformance = Integer.parseInt(st.nextToken());
				bc[i] = new BC(inputX, inputY, inputCoverage, inputPerformance);
			}

			aUser = new User(1, 1);
			bUser = new User(10, 10);

			System.out.println("#" + t + " " + solve());
		}
	}

	private static int solve() {
		int ans = 0;
		for (int time = 0; time <= M; time++) { // 각 시간별 모든 충전소에 접근해서 가장 큰 값을 얻는다.
			// 사용자의 위치 이동(aUser, bUser)
			aUser.r += dr[aPath[time]];
			aUser.c += dc[aPath[time]];
			bUser.r += dr[bPath[time]];
			bUser.c += dc[bPath[time]];

			ans += getCharge(); // 최대 충전량 계산
		}
		return ans;
	}

	private static int getCharge() {
		int max = 0;
		for (int a = 0; a < A; a++) { // a유저 선택 BC
			for (int b = 0; b < A; b++) { // b유저 선택 BC
				int sum = 0;
				int aSum = getBCPerformance(a, aUser);
				int bSum = getBCPerformance(b, bUser);
				if (a != b)
					sum = aSum + bSum;
				else
					sum = Math.max(aSum, bSum);

				if (max < sum)
					max = sum;

			}
		}
		return 0;
	}

	// 배터리의 위치와 사용자의 위치를 계산하여 범위에 들어오는지 확인.
	private static int getBCPerformance(int idx, User user) {
		return (Math.abs(bc[idx].r - user.r) + Math.abs(bc[idx].c - user.c) <= bc[idx].coverage) 
				? bc[idx].performance : 0;
	}
}