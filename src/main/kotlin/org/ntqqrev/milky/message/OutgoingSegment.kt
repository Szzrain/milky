package org.ntqqrev.milky.message

import org.ntqqrev.saltify.composeidl.*
import org.ntqqrev.saltify.composeidl.Array

val OutgoingResourceSegmentBase = Struct {
    field("uri", StringType) {
        describe("文件 URI，支持 `file://` `http(s)://` `base64://` 三种格式")
    }
}

val OutgoingSegment = DiscriminatedUnion("OutgoingSegment", "type") {
    describe("发送消息段")

    structPlacedInto("data")

    defineSharedSegment()

    struct("reply") {
        describe("回复消息段")
        field("message_seq", LongType, "被引用的消息序列号")
    }

    struct("image") {
        describe("图片消息段")
        use(OutgoingResourceSegmentBase)
        field("summary", StringType, "图片预览文本") { optional() }
        field("sub_type", StringType, "图片类型") {
            enum("normal", "sticker")
        }
    }

    struct("record") {
        describe("语音消息段")
        use(OutgoingResourceSegmentBase)
    }

    struct("video") {
        describe("视频消息段")
        use(OutgoingResourceSegmentBase)
        field("thumb_uri", StringType, "封面图片 URI") { optional() }
    }

    struct("forward") {
        describe("合并转发消息段")
        field("messages", Array(OutgoingForwardedMessage), "合并转发 ID")
    }
}