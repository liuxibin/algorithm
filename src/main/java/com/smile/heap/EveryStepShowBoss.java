package com.smile.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op
 * 两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
 * arr= [3,3,1,2,1,2,5…
 * op = [T,T,T,T,F,T,F…
 * 依次表示：
 * 3用户购买了一件商品
 * 3用户购买了一件商品
 * 1用户购买了一件商品…
 * 一对arr[i]和op[i]就代表一个事件：
 * 用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品
 * op[i] == F就代表这个用户退货了一件商品
 * 现在你作为电商平台负责人，你想在每一个事件到来的时候，
 * 都给购买次数最多的前K名用户颁奖。
 * 所以每个事件发生后，你都需要一个得奖名单（得奖区）。
 * 得奖系统的规则：
 * 1，如果某个用户购买商品数为0，但是又发生了退货事件，
 *      则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
 * 2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
 * 3，每次都是最多K个用户得奖，K也为传入的参数
 *       如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
 * 4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，
 *       一定在这两个区域中的一个
 * 5，购买数最大的前K名用户进入得奖区，
 *       在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
 * 6，如果购买数不足以进入得奖区的用户，进入候选区
 * 7，如果候选区购买数最多的用户，已经足以进入得奖区，
 *      该用户就会替换得奖区中购买数最少的用户（大于才能替换），
 *      如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
 *      如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
 * 8，候选区和得奖区是两套时间，
 *      因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
 *      从得奖区出来进入候选区的用户，得奖区时间删除，
 *      进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 *      从候选区出来进入得奖区的用户，候选区时间删除，
 *      进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
 *      离开是指彻底离开，哪个区域也不会找到该用户
 *      如果下次该用户又发生购买行为，产生>0的购买数，
 *      会再次根据之前规则回到某个区域中，进入区域的时间重记
 * 请遍历arr数组和op数组，遍历每一步输出一个得奖名单
 * public List<List<Integer>>  topK (int[] arr, boolean[] op, int k)
 *
 * @author LiuXiBin
 * @since 2024/3/7 15:51
 */
public class EveryStepShowBoss {

	public static class Customer {
		public int id;
		public int buy;
		public int enterTime;

		public Customer(int id, int buyNum, int time) {
			this.id = id;
			this.buy = buyNum;
			this.enterTime = time;
		}
	}

	public static class CandidateComparator implements Comparator<Customer> {
		@Override
		public int compare(Customer o1, Customer o2) {
			return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
		}
	}

	public static class DaddyComparator implements Comparator<Customer> {

		@Override
		public int compare(Customer o1, Customer o2) {
			return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
		}

	}

	public static class HandlePrize {

		// 参与客户 key = 客户ID, value = 客户信息
		private final HashMap<Integer, Customer> customers = new HashMap<>();
		// 候奖区（小根堆，堆顶为购买量最大且先进进入候奖区的客户）
		private final HeapGreater<Customer> candidateHeap = new HeapGreater<>(new CandidateComparator());
		// 获奖区（小根堆，堆顶为购买量最小且先进进入获奖区的客户）
		private final HeapGreater<Customer> prizeWinnerHeap = new HeapGreater<>(new DaddyComparator());
		// 获奖人数限制
		private final int daddyLimit;

		public HandlePrize(int limit) {
			daddyLimit = limit;
		}

		/**
		 * 处理买卖信息
		 * @param time 业务产生次序
		 * @param id 客户Id
		 * @param buyOrRefund 购买or退款
		 * @author LiuXiBin
		 * @since 2024/3/7 14:49
		 */
		public void operate(int time, int id, boolean buyOrRefund) {
			// 用户退款，且当前有效客户中无该客户信息，数据无效
			if (!buyOrRefund && !customers.containsKey(id)) {
				return;
			}
			// 客户不存在则新建
			Customer customer = customers.get(id);
			if (customer == null) {
				// 客户不存在 新建客户
				customer = new Customer(id, 1, time);
				customers.put(id, customer);
				// 进入奖区
				if (prizeWinnerHeap.size() < daddyLimit) {
					// 获奖区未满 直接进入获奖区
					prizeWinnerHeap.push(customer);
				} else {
					candidateHeap.push(customer);
				}
			} else {
				// 处理买卖数量
				customer.buy = buyOrRefund ? customer.buy + 1 : customer.buy - 1;
				if (customer.buy <= 0) {
					// 退单后购买量为0 移除客户
					removeCustomer(customer);
				} else if (candidateHeap.contains(customer)) {
					// 客户在候奖区 调整客户堆位置
					candidateHeap.resign(customer);
				} else {
					// 客户再获奖区 调整客户堆位置
					prizeWinnerHeap.resign(customer);
				}
				// 获奖用户是否发生改变
				prizeWinnerMove(time);
			}
		}

		private void removeCustomer(Customer customer) {
			customers.remove(customer.id);
			if (candidateHeap.contains(customer)) {
				candidateHeap.remove(customer);
			}
			if (prizeWinnerHeap.contains(customer)) {
				prizeWinnerHeap.remove(customer);
			}
		}

		public List<Integer> getDaddies() {
			List<Customer> customers = prizeWinnerHeap.getAllElements();
			List<Integer> ans = new ArrayList<>();
			for (Customer c : customers) {
				ans.add(c.id);
			}
			return ans;
		}

		private void prizeWinnerMove(int time) {
			// 候奖区无客户 无需变换
			if (candidateHeap.size() == 0) {
				return;
			}
			// 获奖区人数不足 直接从候奖区进入(此前该客户在获奖区，本次交易后有效交易为0，从获奖区移除后获奖区人员缺失)
			if (prizeWinnerHeap.size() < daddyLimit) {
				Customer customer = candidateHeap.pop();
				customer.enterTime = time;
				prizeWinnerHeap.push(customer);
			} else if (candidateHeap.peek().buy > prizeWinnerHeap.peek().buy) {
				Customer newWinner = candidateHeap.pop();
				Customer oldWinner = prizeWinnerHeap.pop();
				// 进入获奖区
				newWinner.enterTime = time;
				prizeWinnerHeap.push(newWinner);
				// 进入候奖区
				oldWinner.enterTime = time;
				candidateHeap.push(oldWinner);
			}
		}

	}

	public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
		List<List<Integer>> ans = new ArrayList<>();
		HandlePrize handlePrize = new HandlePrize(k);
		for (int i = 0; i < arr.length; i++) {
			handlePrize.operate(i, arr[i], op[i]);
			ans.add(handlePrize.getDaddies());
		}
		return ans;
	}

	// 干完所有的事，模拟，不优化
	public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
		HashMap<Integer, Customer> map = new HashMap<>();
		ArrayList<Customer> cands = new ArrayList<>();
		ArrayList<Customer> daddy = new ArrayList<>();
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			int id = arr[i];
			boolean buyOrRefund = op[i];
			if (!buyOrRefund && !map.containsKey(id)) {
				ans.add(getCurAns(daddy));
				continue;
			}
			// 没有发生：用户购买数为0并且又退货了
			// 用户之前购买数是0，此时买货事件
			// 用户之前购买数>0， 此时买货
			// 用户之前购买数>0, 此时退货
			if (!map.containsKey(id)) {
				map.put(id, new Customer(id, 0, 0));
			}
			// 买、卖
			Customer c = map.get(id);
			if (buyOrRefund) {
				c.buy++;
			} else {
				c.buy--;
			}
			if (c.buy == 0) {
				map.remove(id);
			}
			// c
			// 下面做
			if (!cands.contains(c) && !daddy.contains(c)) {
				if (daddy.size() < k) {
					c.enterTime = i;
					daddy.add(c);
				} else {
					c.enterTime = i;
					cands.add(c);
				}
			}
			cleanZeroBuy(cands);
			cleanZeroBuy(daddy);
			cands.sort(new CandidateComparator());
			daddy.sort(new DaddyComparator());
			move(cands, daddy, k, i);
			ans.add(getCurAns(daddy));
		}
		return ans;
	}

	public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
		if (cands.isEmpty()) {
			return;
		}
		// 候选区不为空
		if (daddy.size() < k) {
			Customer c = cands.get(0);
			c.enterTime = time;
			daddy.add(c);
			cands.remove(0);
		} else { // 等奖区满了，候选区有东西
			if (cands.get(0).buy > daddy.get(0).buy) {
				Customer oldDaddy = daddy.get(0);
				daddy.remove(0);
				Customer newDaddy = cands.get(0);
				cands.remove(0);
				newDaddy.enterTime = time;
				oldDaddy.enterTime = time;
				daddy.add(newDaddy);
				cands.add(oldDaddy);
			}
		}
	}

	public static void cleanZeroBuy(ArrayList<Customer> arr) {
		List<Customer> noZero = new ArrayList<Customer>();
		for (Customer c : arr) {
			if (c.buy != 0) {
				noZero.add(c);
			}
		}
		arr.clear();
		for (Customer c : noZero) {
			arr.add(c);
		}
	}

	public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
		List<Integer> ans = new ArrayList<>();
		for (Customer c : daddy) {
			ans.add(c.id);
		}
		return ans;
	}

	// 为了测试
	public static class Data {
		public int[] arr;
		public boolean[] op;

		public Data(int[] a, boolean[] o) {
			arr = a;
			op = o;
		}
	}

	// 为了测试
	public static Data randomData(int maxValue, int maxLen) {
		int len = (int) (Math.random() * maxLen) + 1;
		int[] arr = new int[len];
		boolean[] op = new boolean[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * maxValue);
			op[i] = Math.random() < 0.5 ? true : false;
		}
		return new Data(arr, op);
	}

	// 为了测试
	public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
		if (ans1.size() != ans2.size()) {
			return false;
		}
		for (int i = 0; i < ans1.size(); i++) {
			List<Integer> cur1 = ans1.get(i);
			List<Integer> cur2 = ans2.get(i);
			if (cur1.size() != cur2.size()) {
				return false;
			}
			cur1.sort((a, b) -> a - b);
			cur2.sort((a, b) -> a - b);
			for (int j = 0; j < cur1.size(); j++) {
				if (!cur1.get(j).equals(cur2.get(j))) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int maxValue = 1000;
		int maxLen = 10000;
		int maxK = 10;
		int testTimes = 10000;
		System.out.println("测试开始");
		long countTimeTopK = 0;
		long countTimeCompare = 0;
		for (int i = 0; i < testTimes; i++) {
			Data testData = randomData(maxValue, maxLen);
			int k = (int) (Math.random() * maxK) + 1;
			int[] arr = testData.arr;
			boolean[] op = testData.op;
			long millis1 = System.currentTimeMillis();
			List<List<Integer>> ans1 = topK(arr, op, k);
			long millis2 = System.currentTimeMillis();
			countTimeTopK += millis2 - millis1;
			List<List<Integer>> ans2 = compare(arr, op, k);
			long millis3 = System.currentTimeMillis();
			countTimeCompare += millis3 - millis2;
			if (!sameAnswer(ans1, ans2)) {
				for (int j = 0; j < arr.length; j++) {
					System.out.println(arr[j] + " , " + op[j]);
				}
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("topK总耗时：" + countTimeTopK + "ms");//16253ms
		System.out.println("compare总耗时：" + countTimeCompare + "ms");//397894ms
		System.out.println("测试结束");
	}

}