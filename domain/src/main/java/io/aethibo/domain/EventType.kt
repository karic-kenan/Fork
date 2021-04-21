/*
 * Created by Karic Kenan on 19.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain

enum class EventType {
    IssuesEvent,
    IssueCommentEvent,
    PushEvent,
    PublicEvent,
    WatchEvent,
    CreateEvent,
    ForkEvent
}