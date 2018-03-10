package model

object MessageType extends Enumeration {
  type WeekDay = Value
  val INBOX, OUTBOX = Value
}