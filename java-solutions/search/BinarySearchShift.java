package search;

public class BinarySearchShift {
//    Pred:
//      String[] args - array && args[1:] - sored array[medium2 --> big, small --> medium1]
//    Post:
//      (R: args[R] <= x && a[R - 1] > x && R != 0 ||
//      R == 0 && (args.length == 0) ||
//      R == args.length)
//      && args - partially sorted array (medium2 --> big, small --> medium1)
    public static void main (String[] args) {
        int x = Integer.parseInt(args[0]);

//        begin >= 0 && end <= args.length
//        && begin < end
//        && (args[begin] > x >= args[end])
//        && args - partially sorted array (medium2 --> big, small --> medium1)
        int y = recSearch(args,0, args.length, x) % args.length;
//        R: args[R] <= x && a[R - 1] > x && R != 0 ||
//        R == 0 && (args.length == 0) ||
//        R == args.length
//        && args - partially sorted array (medium2 --> big, small --> medium1)

//        args - sorted array (medium2 --> big, small --> medium1)
        int z = iterSearch(args, x) % args.length;
//        R: args[R] <= x && a[R - 1] > x && R != 0 ||
//        R == 0 && (args.length == 0) ||
//        R == args.length

        if (y == z) {
            System.out.println(y);
        }
    }

//    Pred:
//      begin >= 0 && end <= args.length
//      && begin < end
//      && (args[begin] > x >= args[end])
//      && args - partially sorted array (medium2 --> big, small --> medium1)
//    Post:
//      R: args[R] <= x && a[R - 1] > x && R != 0 ||
//      R == 0 && (args.length == 0) ||
//      R == args.length
//    && args - partially sorted array (medium2 --> big, small --> medium1)
    public static int recSearch(String[] args, int begin, int end, int x) {
//        begin >= 0 && end <= args.length
//        && begin < end
//        && (args[begin] > x >= args[end])
//        && args - partially sorted array (medium2 --> big, small --> medium1)
        if (end - begin <= 1) {
//            begin >= 0 && end <= args.length
//            && begin < end && args - partially sorted array (medium2 --> big, small --> medium1)
//            end - begin == 1
//            args[begin] > x >= args[end]
//            begin < answer <= end (answer - number we must receive and return)
//            ==> end == answer
//            R = end:

//            R: args[R] <= x && a[R - 1] > x && R != 0 ||
//            R == 0 && (args.length == 0) ||
//            R == args.length
            return end;
        }
//        args - sorted array (big<--small)
//        end - begin >= 2
//        begin + end >= (2 + begin) >= 2
//        (begin + end) / 2 >= 1
//        begin >= 0 && end <= args.length && begin < end && (args[begin] > x >= args[end])
        int m = (begin + end) / 2;
//        (begin < end) ==> (begin < m < end)
        if (Integer.parseInt(args[m]) < x) {
//            args - partially sorted array (medium2 --> big, small --> medium1)
//            for i = m+1..end: args[i] <= x (we can ignore it)

//            Pred:
//          begin' >= begin >= 0 && end' <= end <= args.length
//          && begin' < end'
//          && (args[begin'] > x >= args[end'])
//          && args - partially sorted array (medium2 --> big, small --> medium1)
            return recSearch(args, begin, m, x);
//            Post:
//          R: args[R] <= x && a[R - 1] > x && R != 0 ||
//          R == 0 && (args.length == 0) ||
//          R == args.length
//          && args - partially sorted array (medium2 --> big, small --> medium1)
        } else {
//            args - partially sorted array (medium2 --> big, small --> medium1)
//            for i = begin, .. , m: args[i] > x (we can ignore it)
//
//           Pred:
//          begin' >= begin >= 0 && end' <= end <= args.length
//          && begin' < end'
//          && (args[begin'] > x >= args[end'])
//          && args - partially sorted array (medium2 --> big, small --> medium1)
            return recSearch(args, m, end, x);
//           Post:
//          R: args[R] <= x && a[R - 1] > x && R != 0 ||
//          R == 0 && (args.length == 0) ||
//          R == args.length
//          && args - partially sorted array (medium2 --> big, small --> medium1)
        }
    }

//    Pred:
//      args - sorted array (medium2 --> big, small --> medium1)
//    Post:
//      R: args[R] <= x && a[R - 1] > x && R != 0 ||
//      R == 0 && (args.length == 0) ||
//      R == args.length
    private static int iterSearch(String[] args, int x) {
        int begin = 0;
        int end = args.length;
//        begin >= 0 && end <= args.length
//        && begin < end
//        && (args[begin] > x >= args[end])
//        && args - sorted array (big<--small)
        while ((end - begin) != 1) {
            int m = (begin + end) / 2;
//            (begin < end) ==> (begin < m < end)
            if (Integer.parseInt(args[m]) < x) {
//                (begin < m < end)
//                args - sorted array (medium2 --> big, small --> medium1)
//                for i = m+1..end: args[i] <= x (we can ignore it)
                end = m;
//                begin' >= begin >= 0 && end' <= end <= args.length
//                && begin' < end'
//                && (args[begin'] > x >= args[end'])
//                && args - sorted array (medium2 --> big, small --> medium1)
            } else {
//                (begin < m < end)
//                args - sorted array (medium2 --> big, small --> medium1)
//                for i = begin, .. , m: args[i] > x (we can ignore it)
                begin = m;
//                begin' >= begin >= 0 && end' <= end <= args.length
//                && begin' < end'
//                && (args[begin'] > x >= args[end'])
//                && args - sorted array (medium2 --> big, small --> medium1)
            }
//            begin' >= begin >= 0 && end' <= end <= args.length
//            && begin' < end'
//            && (args[begin'] > x >= args[end'])
//            && args - sorted array (medium2 --> big, small --> medium1)
        }
//        begin >= 0 && end <= args.length && begin < end && args - sorted array (medium2 --> big, small --> medium1)
//        end - begin == 1
//        args[begin] > x >= args[end]
//        begin < answer <= end (answer - number we must receive and return)
//        ==> end == answer
//        answer = end
//        R = end:
//
//       R: args[R] <= x && a[R - 1] > x && R != 0 ||
//      R == 0 && (args.length == 0) ||
//      R == args.length
        return end;
    }
}
