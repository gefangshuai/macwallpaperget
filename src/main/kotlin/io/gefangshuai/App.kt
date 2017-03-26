package io.gefangshuai

const val FILE_DIRECTORY = "/Users/gefangshuai/Pictures/wallpapers/"
fun main(args: Array<String>) {
    println(if (args != null && args.size > 0) args[0].toInt() else 1)
    var service = FetchService(if (args != null && args.size > 0) args[0].toInt() else 1)
    println(service.fetch(FILE_DIRECTORY))
//    println("http://wallpapershome.com/images/wallpapers/apple-3360x2100-ios-10-4k-5k-live-wallpaper-live-photo-wave-macos-11998.jpg".substringAfterLast("/"))
}

