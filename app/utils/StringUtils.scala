package utils

object StringUtils {
  def isBlank(string:String):Boolean = string == null || string.trim.isEmpty
}
