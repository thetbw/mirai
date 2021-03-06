/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

@file:JvmMultifileClass
@file:JvmName("Utils")

package net.mamoe.mirai.utils

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * 要求 [this] 最小为 [min].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Int.coerceAtLeastOrFail(min: Int): Int {
    require(this >= min)
    return this
}

/**
 * 要求 [this] 最小为 [min].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Long.coerceAtLeastOrFail(min: Long): Long {
    require(this >= min)
    return this
}

/**
 * 要求 [this] 最大为 [max].
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Int.coerceAtMostOrFail(max: Int): Int =
    if (this >= max) error("value is greater than its expected maximum value $max")
    else this

@Suppress("NOTHING_TO_INLINE")
inline fun Long.coerceAtMostOrFail(max: Long): Long =
    if (this >= max) error("value is greater than its expected maximum value $max")
    else this