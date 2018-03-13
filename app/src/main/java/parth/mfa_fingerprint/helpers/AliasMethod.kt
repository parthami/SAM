package parth.mfa_fingerprint.helpers

import android.util.Log
import java.util.*

/**
 * Created by Parth Chandratreya on 13/03/2018.
 */
class AliasMethod(var incoming: DoubleArray) {

    private var alias: IntArray
    private var probabilities: DoubleArray
    private var random: Random = Random()

    init {
//      Create arrays Alias and Prob, each of size n
        val n = incoming.size
        alias = IntArray(n)
        probabilities = DoubleArray(n)
//      Create two worklists, Small and Large.
        val small = ArrayDeque<Int>()
        val large = ArrayDeque<Int>()
//      Calculate the average
        val average: Double = 1.0 / incoming.size
        val initialArray = incoming
//      For each  probability pi:
//      If pi<1, add i to Small.
//      Otherwise (pi≥1), add i to Large.
        for (i in initialArray.indices) {
            if (initialArray[i] >= average)
                large.add(i)
            else
                small.add(i)
        }
//        While Small and Large are not empty: (Large might be emptied first)
        while (!small.isEmpty() and !large.isEmpty()) {
//        Remove the first element from Small; call it l.
            val l = small.removeLast()
//        Remove the first element from Large; call it g.
            val g = large.removeLast()
//        Multiply each probability by n.
//        Set Prob[l]=pl.
            probabilities[l] = initialArray[l] * n
//        Set Alias[l]=g.
            alias[l] = g
//        Set pg:=(pg+pl)−1. (This is a more numerically stable option.)
            initialArray[g] = initialArray[g] + initialArray[l] - average
//        If pg<1, add g to Small.
//        Otherwise (pg≥1), add g to Large.
            if (initialArray[g] < average)
                small.add(g)
            else
                large.add(g)
        }
        Log.i("PTAG", probabilities.toString())
        Log.i("PTAG", alias.toString())
//        While Large is not empty:
//        Remove the first element from Large; call it g.
//        Set Prob[g]=1.0
        while (!large.isEmpty())
            probabilities[large.removeLast()] = 1.0
//        While Small is not empty: This is only possible due to numerical instability.
//        Remove the first element from Small; call it l.
//        Set Prob[l]=1.
        while (!small.isEmpty())
            probabilities[small.removeLast()] = 1.0
        var finsihed = 1
    }

    fun generation(): Int {
        // Generate a fair die roll from an n-sided die; call the side i.
        val fairDiceRoll = random.nextInt(probabilities.size)
        // Flip a biased coin that comes up heads with probability Prob[i].
        val biasedCoinFlip = random.nextDouble() < probabilities[fairDiceRoll]
        //  If the coin comes up "heads," return i.  Otherwise, return Alias[i].
        return if (biasedCoinFlip) fairDiceRoll else alias[fairDiceRoll]

    }
}