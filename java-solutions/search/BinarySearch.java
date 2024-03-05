package search;

public class BinarySearch {
//    Pred:
//      String[] args - array && args[1:] - sored array[big <-- small]
//    Post:
//      R: args[R + 1] <= x && a[R] > x && R != 0 ||
//      R == 0 && (args.length == 1) ||
//      R == args.length - 1
    public static void main (String[] args) {
//        String[] args - array && args[1:] - sored array[big <-- small]
        int x = Integer.parseInt(args[0]);

//        String[] args - array && args[1:] - sored array[big <-- small]
//        begin >= 0 && end <= args.length
//        && begin < end
//        && (args[begin] > x >= args[end])
        int y = recSearch(args,0, args.length, x);
//        R: args[R + 1] <= x && a[R] > x && R != 0 ||
//        R == 0 && (args.length == 1) ||
//        R == args.length - 1

//        args - sorted array (big<--small)
        int z = iterSearch(args, x);
//        R: args[R + 1] <= x && a[R] > x && R != 0 ||
//        R == 0 && (args.length == 1) ||
//        R == args.length - 1

//        true
        if (y == z) {
            System.out.println(y);
        }
//        y == z && y
    }


//    Pred:
//      begin >= 0 && end <= args.length
//      && begin < end
//      && (args[begin] > x >= args[end])
//      && args - sorted array (big<--small)
//    Post:
//      R: args[R + 1] <= x && a[R] > x && R != 0 ||
//      R == 0 && (args.length == 1) ||
//      R == args.length - 1
    public static int recSearch(String[] args, int begin, int end, int x) {
//      begin >= 0 && end <= args.length
//      && begin < end
//      && (args[begin] > x >= args[end])
//      && args - sorted array (big<--small)

        if (end - begin == 1) {
//            begin >= 0 && end <= args.length && begin < end && args - sorted array (big<--small)
//            end - begin == 1
//            args[begin] > x >= args[end]
//            begin < answer + 1 <= end (answer - number we must receive and return)
//            ==> end == answer + 1
//            answer = end - 1
//            R = end - 1:

//            R: args[R + 1] <= x && a[R] > x && R != 0 ||
//            R == 0 && (args.length == 1) ||
//            R == args.length - 1                             (if end == args.length)
            return end - 1;
        }
//        args - sorted array (big<--small)
//        end - begin >= 2
//        begin + end >= (2 + begin) >= 2
//        (begin + end) / 2 >= 1
//        begin >= 0 && end <= args.length && begin < end && (args[begin] > x >= args[end])
        int m = (begin + end) / 2;
//        (begin < end) ==> (begin < m < end)
        if (Integer.parseInt(args[m]) <= x) {
//            args - sorted array (big<--small)
//            for i = m+1..end: args[i] <= x (we can ignore it)

//          begin' >= begin >= 0 && end' <= end <= args.length
//          && begin' < end'
//          && (args[begin'] > x >= args[end'])
//          && args - sorted array (big<--small)
            return recSearch(args, begin, m, x);
//            Post:
//            R: args[R + 1] <= x && a[R] > x && R != 0 ||
//            R == 0 && (args.length == 1) ||
//            R == args.length - 1
        } else {
//            for i = begin, .. , m: args[i] > x (we can ignore it)
//
//            Pred:
//          begin' >= begin >= 0 && end' <= end <= args.length
//          && begin' < end'
//          && (args[begin'] > x >= args[end'])
//          && args - sorted array (big<--small)
            return recSearch(args, m, end, x);
//            Post:
//            R: args[R + 1] <= x && a[R] > x && R != 0 ||
//            R == 0 && (args.length == 1) ||
//            R == args.length - 1
        }
    }


//     Pred:
//      args - sorted array (big<--small)
//     Post:
//      R: args[R + 1] <= x && a[R] > x && R != 0 ||
//      R == 0 && (args.length == 1) ||
//      R == args.length - 1
    private static int iterSearch(String[] args, int x) {
        int begin = 0;
        int end = args.length;

//      begin >= 0 && end <= args.length
//      && begin < end
//      && (args[begin] > x >= args[end])
//      && args - sorted array (big<--small)
        while ((end - begin) > 1) {
            int m = (begin + end) / 2;
//            (begin < end) ==> (begin < m < end)
            if (Integer.parseInt(args[m]) <= x) {
//                (begin < m < end)
//                args - sorted array (big<--small)
//                for i = m+1..end: args[i] <= x (we can ignore it)
                end = m;
//                begin' >= begin >= 0 && end' <= end <= args.length
//                && begin' < end'
//                && (args[begin'] > x >= args[end'])
//                && args - sorted array (big<--small)
            } else {
//                (begin < m < end)
//                args - sorted array (big<--small)
//                for i = begin, .. , m: args[i] > x (we can ignore it)
                begin = m;
//                begin' >= begin >= 0 && end' <= end <= args.length
//                && begin' < end'
//                && (args[begin'] > x >= args[end'])
//                && args - sorted array (big<--small)
            }
//            begin' >= begin >= 0 && end' <= end <= args.length
//            && begin' < end'
//            && (args[begin'] > x >= args[end'])
//            && args - sorted array (big<--small)
        }

//        begin >= 0 && end <= args.length && begin < end && args - sorted array (big<--small)
//        end - begin == 1
//        args[begin] > x >= args[end]
//        begin < answer + 1 <= end (answer - number we must receive and return)
//        ==> end == answer + 1
//        answer = end - 1
//        R = end - 1:
//
//        R: args[R + 1] <= x && a[R] > x && R != 0 ||
//        R == 0 && (args.length == 1) ||
//        R == args.length - 1                             (if end == args.length)
        return end - 1;
    }
}