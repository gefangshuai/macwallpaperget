package io.gefangshuai

import org.apache.commons.io.IOUtils
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.net.URL


/**
 * Created by gefangshuai on 2017/3/25.
 */
class FetchService() {
    private val url: String = "http://wallpapershome.com/download-wallpapers/macbook/"
    var page: Int = 1

    constructor(page: Int) : this() {
        this.page = page
    }

    private fun getNextUrl(): String {
        if (page > 1)
            return "$url?page=$page"
        else
            return url
    }


    fun fetch(output: String) {
        println("-------start fetch page $page--------")
        val currentUrl = getNextUrl()
        println("current url: $currentUrl")
        val document = Jsoup.connect(currentUrl).get()
        val elements = document.select("img.hor").iterator()
        while (elements.hasNext()) {
            val img = elements.next()
            if (img.attr("src").contains("ads"))
                continue
            val element = img.parent()
            val imgPageUrl = element.attr("abs:href")
            println("img page url: $imgPageUrl")
            val imgUrl = Jsoup.connect(imgPageUrl).get().body().select("div.pic-left>div>a").first().attr("abs:href")
            println("img url: $imgUrl")
            val filePath = "${output.removeSuffix("/")}/${imgUrl.substringAfterLast("/")}"
            println("file save: $filePath")
            writeImg(imgUrl, filePath)
        }
        page++
        fetch(output)
    }


    fun writeImg(imgUrl: String, path: String) {
        val url = URL(imgUrl)
        val inp = url.openStream()
        val os = FileOutputStream(path)

        IOUtils.copyLarge(inp, os)
    }
}