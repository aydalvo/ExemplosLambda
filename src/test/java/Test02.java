public class Test02 {
    static int missingNumber(int[] arr) {
        int n = arr.length + 1;

        // Calculate the sum of array elements
        int sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += arr[i];
        }

        // Calculate the expected sum
        int expectedSum = (n * (n + 1)) / 2;

        // Return the missing number
        return expectedSum - sum;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 5 };

        System.out.println(missingNumber(arr));
    }
}
