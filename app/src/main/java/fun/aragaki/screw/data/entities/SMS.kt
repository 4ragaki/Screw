package `fun`.aragaki.screw.data.entities

import com.tickaroo.tikxml.annotation.*

@Xml
data class SMS(
    @Element(name = "message")
    val message: SmsMessage?
) {
    @Xml
    data class SmsMessage(
        @Element(name = "flag")
        val flag: SmsMsgFlag?,
        @Element(name = "get_message")
        val getMessage: SmsMsgGetMessage?,
        @Element(name = "set_message")
        val setMessage: SmsMsgSetMessage?,
        @Element(name = "send_save_message")
        val sendSaveMessage: SmsMsgSendSaveMessage?,
        @Element(name = "sms_setting")
        val smsSettings: SmsMsgSmsSetting?,
        @Element(name = "sms_capacity_info")
        val smsCapacityInfo: SmsMsgSmsCapacityInfo?,
        @PropertyElement(name = "sms_result")
        val smsResult: String?,
        @PropertyElement(name = "sms_report_status_list")
        val smsReportStatusList: String?
    ) {
        @Xml
        data class SmsMsgFlag(
            @PropertyElement(name = "message_flag")
            val messageFlag: String?,
            @PropertyElement(name = "sms_cmd")
            val smsCmd: String?,
            @PropertyElement(name = "sms_cmd_status_result")
            val smsCmdStatusResult: Int?
        )

        @Xml
        data class SmsMsgGetMessage(
            @PropertyElement(name = "tags")
            val tags: String?,
            @PropertyElement(name = "mem_store")
            val mem_store: String?,
            @Path("message_list")
            @Element(name = "Item")
            val messageList: List<SmsMsgGetMessageMsg>?,
            @PropertyElement(name = "page_number")
            val pageNumber: String?,
            @PropertyElement(name = "total_number")
            val totalNumber: String?
        ) {
            @Xml
            data class SmsMsgGetMessageMsg(
                @Attribute(name = "index")
                val index: String?,

                @PropertyElement(name = "index")
                val ind3x: String?,
                @PropertyElement(name = "from")
                val from: AsciiedUnicode?,
                @PropertyElement(name = "subject")
                val subject: AsciiedUnicode?,
                @PropertyElement(name = "received")
                val received: String?,
                @PropertyElement(name = "status")
                val status: String?,
                @PropertyElement(name = "message_type")
                val message_type: String?,
                @PropertyElement(name = "class_type")
                val class_type: String?
            )
        }

        @Xml
        data class SmsMsgSetMessage(
            @PropertyElement(name = "read_message_id")
            val readMessageId: String?,
            @PropertyElement(name = "delete_message_id")
            val deleteMessageId: String?,
            @PropertyElement(name = "mv_cp_id")
            val mvCpId: String?
        )

        @Xml
        data class SmsMsgSendSaveMessage(
            @PropertyElement(name = "contacts")
            val contacts: String?,
            @PropertyElement(name = "content")
            val content: String?,
            @PropertyElement(name = "encode_type")
            val encodeType: String?,
            @PropertyElement(name = "sms_time")
            val smsTime: String?,
            @PropertyElement(name = "edit_draft_id")
            val editDraftId: String?,
            @PropertyElement(name = "send_from_draft_id")
            val sendFromDraftId: String?

        )

        @Xml
        data class SmsMsgSmsSetting(
            @PropertyElement(name = "status_report")
            val statusReport: String?,
            @PropertyElement(name = "save_time")
            val saveTime: String?,
            @PropertyElement(name = "save_location")
            val saveLocation: String?,
            @PropertyElement(name = "sms_center")
            val smsCenter: String?,
            @PropertyElement(name = "sms_over_cs")
            val smsOverCs: String?
        )

        @Xml
        data class SmsMsgSmsCapacityInfo(
            @PropertyElement(name = "sms_nv_total")
            val smsNvTotal: String?,
            @PropertyElement(name = "sms_sim_total")
            val smsSimTotal: String?,
            @PropertyElement(name = "sms_sim_num")
            val smsSimNum: String?,
            @PropertyElement(name = "sms_nv_rev_total")
            val smsNvRevTotal: String?,
            @PropertyElement(name = "sms_nv_rev_num")
            val smsNvRevNum: String?,
            @PropertyElement(name = "sms_nv_send_total")
            val smsNvSendTotal: String?,
            @PropertyElement(name = "sms_nv_send_num")
            val smsNvSendNum: String?,
            @PropertyElement(name = "sms_nv_draftbox_total")
            val smsNvDraftboxTotal: String?,
            @PropertyElement(name = "sms_nv_draftbox_num")
            val smsNvDraftboxNum: String?,
            @PropertyElement(name = "sms_sim_rev_total")
            val smsSimRevTotal: String?,
            @PropertyElement(name = "sms_sim_send_total")
            val smsSimSendTotal: String?,
            @PropertyElement(name = "sms_sim_draftbox_total")
            val smsSimDraftboxTotal: String?,
            @PropertyElement(name = "sms_nv_rev_long_num")
            val smsNvRevLongNum: String?,
            @PropertyElement(name = "sms_nv_send_long_num")
            val smsNvSendLongNum: String?,
            @PropertyElement(name = "sms_sim_long_num")
            val smsSimLongNum: String?
        )
    }
}