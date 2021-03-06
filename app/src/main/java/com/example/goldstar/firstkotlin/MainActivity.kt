package com.example.goldstar.firstkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView


private val inputString: String = "Using Kotlin for Android Development Kotlin is a great fit for developing Android applications, bringing all of the advantages of a modern language to the Android platform without introducing any new restrictions: Compatibility: Kotlin is fully compatible with JDK 6, ensuring that Kotlin applications can run on older Android devices with no issues. The Kotlin tooling is fully supported in Android Studio and compatible with the Android build system. Performance: A Kotlin application runs as fast as an equivalent Java one, thanks to very similar bytecode structure. With Kotlin's support for inline functions, code using lambdas often runs even faster than the same code written in Java. Interoperability: Kotlin is 100% interoperable with Java, allowing to use all existing Android libraries in a Kotlin application. This includes annotation processing, so databinding and Dagger work too. Footprint: Kotlin has a very compact runtime library, which can be further reduced through the use of ProGuard. In a real application, the Kotlin runtime adds only a few hundred methods and less than 100K to the size of the .apk file. Compilation Time: Kotlin supports efficient incremental compilation, so while there's some additional overhead for clean builds, incremental builds are usually as fast or faster than with Java. Learning Curve: For a Java developer, getting started with Kotlin is very easy. The automated Java to Kotlin converter included in the Kotlin plugin helps with the first steps. Kotlin Koans offer a guide through the key features of the language with a series of interactive exercises. Kotlin for Android Case Studies Kotlin has been successfully adopted by major companies, and a few of them have shared their experiences: Pinterest has successfully introduced Kotlin into their application, used by 150M people every month. Basecamp's Android app is 100% Kotlin code, and they report a huge difference in programmer happiness and great improvements in work quality and speed. Keepsafe's App Lock app has also been converted to 100% Kotlin, leading to a 30% decrease in source line count and 10% decrease in method count. Tools for Android Development The Kotlin team offers a set of tools for Android development that goes beyond the standard language features: Kotlin Android Extensions is a compiler extension that allows you to get rid of findViewById() calls in your code and to replace them with synthetic compiler-generated properties. Anko is a library providing a set of Kotlin-friendly wrappers around the Android APIs, as well as a DSL that lets your replace your layout .xml files with Kotlin code. Next Steps Download an install Android Studio 3.0 Preview, which includes Kotlin support out of the box. Follow the Getting Started with Android and Kotlin tutorial to create your first Kotlin application. For a more in-depth introduction, check out the reference documentation on this site and Kotlin Koans. Another great resource is Kotlin for Android Developers, a book that guides you step by step through the process of creating a real Android application in Kotlin. Check out Google's sample projects written in Kotlin."


class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView : TextView = findViewById(R.id.main_text_view)

        var checker = CheckInput(inputString)
        checker.startCleaning()
        var finalWord = checker.getFinalOutput()


        //Sets color of text view based on the first letter of the final word
        when(finalWord[0].toInt() % 4)
        {
            0 -> textView.setBackgroundColor(Color.GOLD.rgb)
            1 ->
            {
                textView.setBackgroundColor(Color.PURPLE.rgb)
                textView.setTextColor(android.graphics.Color.WHITE)
            }
            2 ->
            {
                textView.setBackgroundColor(Color.RED.rgb)
                textView.setTextColor(android.graphics.Color.WHITE)
            }
            3 ->
            {
                textView.setBackgroundColor(Color.BLACK.rgb)
                textView.setTextColor(android.graphics.Color.WHITE)
            }
        }

        //Logs out the word, sets text string
        OutputWord.logOut(finalWord)
        textView.text = finalWord

    }
}

enum class Color( val rgb: Int)
{
    GOLD(android.graphics.Color.rgb(255,195,0)),
    RED(android.graphics.Color.rgb(255,0,0)),
    BLACK(android.graphics.Color.rgb(0,0,0)),
    PURPLE(android.graphics.Color.rgb(128,0,128))
}

object OutputWord
{
    fun logOut(theWord : String)
    {
        Log.e("Output Word", theWord)
    }
}

class CheckInput(private val theString : String)
{
    //Creates regex to remove punctuation, numbers, and capital letters
    private var regexRemove = Regex("^[A-Z].*")
    private var finalString : String = ""


    fun startCleaning()
    {
        //Splits string into words based on whitespace
        //IF it needs to have punctuation at the end included
        //var stringAsList : List<String> = inputString.split(" ")
        //IF it needs to have punctuation at the end removed
        var stringAsList: List<String> = theString.split(" ", ",", "?", "!", ".")

        // Reduces the main list into only lowercase words, with a letter count that is even
        var reducedList: List<String> = stringAsList
                .filter { it.matches(regexRemove) }
                .filter { it.count() % 2 == 0 }

        //Maps the list by word and number of appearances, then removes those with only one
        var reducedMap = reducedList
                .groupingBy { it }.eachCount()
                .filter { it.value > 1 }

        //Sorts the map by length of word
        var finalReducedMap = reducedMap.keys.sortedBy { it -> it.length }
        finalReducedMap = finalReducedMap.reversed()

        //Final longest number, and list for all the words that are that long
        var longestLength: Int = finalReducedMap[0].length
        var finalList: MutableList<String> = finalReducedMap
                .filter { it.length == longestLength }
                .toMutableList()

        //If multiple words are longest length, arranges by the first letter and stores earliest
        // in index 0
        if(finalList.count() > 1)
        {
            finalList.sortBy { it -> it.toCharArray().first()}
            var letter = finalList[0].toCharArray().first()
            finalList = finalList.filter { it.toCharArray().first() == letter}.toMutableList()

            if( finalList.count() > 1)
            {
                finalList.sortBy { it -> stringAsList.indexOf(it) }
            }
        }

        finalString = finalList[0]
    }


    fun getFinalOutput(): String
    {
        return finalString
    }

}