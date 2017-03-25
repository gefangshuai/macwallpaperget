package io.gefangshuai

import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import java.io.File

/**
 * Created by gefangshuai on 2017/3/25.
 */
class FetchService {
    private val url: String = "http://wallpapershome.com/download-wallpapers/macbook/"
    var page: Int = 0
    private fun getNextUrl(): String {
        page ++
        if (page > 1)
           return "$url?page=$page"
        else
            return url
    }


    fun fetch(output: String) {
        println("-------start fetch page $page--------")
        val currentUrl = getNextUrl()
        val document = Jsoup.connect(currentUrl).get()
        val elements = document.select("div.pics>p>a").iterator()
        while (elements.hasNext()) {
            val element = elements.next()
            val imgPageUrl = element.attr("abs:href")
            println("img page url: $imgPageUrl")
            val imgUrl = Jsoup.connect(imgPageUrl).get().body().select("div.pic-left>div>a").first().attr("abs:href")
            println("img url: $imgUrl")
            val filePath = "${output.removeSuffix("/")}/${imgUrl.substringAfterLast("/")}"
            FileUtils.writeByteArrayToFile(File(filePath), Jsoup.connect(imgUrl).ignoreContentType(true).execute().bodyAsBytes(), false)
            println("file save: $filePath")
        }
        fetch(output)
    }
}