package model

import java.time.LocalDateTime

case class Message(
                    var id: Long,
                    var messageType: MessageType.Value,
                    var text: String,
                    var date: LocalDateTime
                  ) {}