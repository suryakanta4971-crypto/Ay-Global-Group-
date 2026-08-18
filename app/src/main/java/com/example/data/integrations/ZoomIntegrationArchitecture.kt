package com.example.data.integrations

/**
 * Zoom Video SDK & OAuth 2.0 Webhook/REST Integration Architecture
 * Manages live classroom initialization, JWT signature creation, and in-app conference state.
 */
object ZoomIntegrationArchitecture {

    data class ZoomMeetingSession(
        val meetingNumber: String,
        val role: Int = 0, // 0 for attendee, 1 for host
        val userName: String,
        val userEmail: String,
        val sdkKey: String = "ZOOM_CLIENT_ID_PLACEHOLDER",
        val signatureToken: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        val isAudioMuted: Boolean = false,
        val isVideoEnabled: Boolean = true,
        val isScreenSharing: Boolean = false
    )

    data class Participant(
        val id: String,
        val name: String,
        val role: String,
        val isMuted: Boolean,
        val isVideoOn: Boolean,
        val isHandRaised: Boolean = false
    )

    fun createClassroomRoster(instructorName: String, currentUserName: String): List<Participant> {
        return listOf(
            Participant("1", instructorName, "Lead Instructor / Host", isMuted = false, isVideoOn = true),
            Participant("2", currentUserName, "Member (You)", isMuted = true, isVideoOn = true),
            Participant("3", "Dr. Sophia Chen", "Guest Mentor", isMuted = false, isVideoOn = true),
            Participant("4", "Marcus Vance", "Member", isMuted = true, isVideoOn = false),
            Participant("5", "Elena Rostova", "Member", isMuted = true, isVideoOn = true),
            Participant("6", "Jordan Lee", "Member", isMuted = true, isVideoOn = false)
        )
    }
}
