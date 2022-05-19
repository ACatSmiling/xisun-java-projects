// **********************************************************************
//
// Copyright (c) 2003-2010 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.4.1

package cn.xisun.emqx.consumer.bean;

// <auto-generated>
//
// Generated from file `AT_RTDB_API.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>

import lombok.ToString;

@ToString
public class ATRTDBDataItem implements Cloneable, java.io.Serializable {
    public String tagName;

    public short quality;

    public double timestamp;

    public double value;

    public ATRTDBDataItem() {
    }

    public ATRTDBDataItem(String tagName, short quality, double timestamp, double value) {
        this.tagName = tagName;
        this.quality = quality;
        this.timestamp = timestamp;
        this.value = value;
    }

    public boolean
    equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        ATRTDBDataItem _r = null;
        try {
            _r = (ATRTDBDataItem) rhs;
        } catch (ClassCastException ex) {
        }

        if (_r != null) {
            if (tagName != _r.tagName) {
                if (tagName == null || _r.tagName == null || !tagName.equals(_r.tagName)) {
                    return false;
                }
            }
            if (quality != _r.quality) {
                return false;
            }
            if (timestamp != _r.timestamp) {
                return false;
            }
            if (value != _r.value) {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode() {
        int __h = 0;
        if (tagName != null) {
            __h = 5 * __h + tagName.hashCode();
        }
        __h = 5 * __h + (int) quality;
        __h = 5 * __h + (int) Double.doubleToLongBits(timestamp);
        __h = 5 * __h + (int) Double.doubleToLongBits(value);
        return __h;
    }

    public Object
    clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException ex) {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os) {
        __os.writeString(tagName);
        __os.writeShort(quality);
        __os.writeDouble(timestamp);
        __os.writeDouble(value);
    }

    public void
    __read(IceInternal.BasicStream __is) {
        tagName = __is.readString();
        quality = __is.readShort();
        timestamp = __is.readDouble();
        value = __is.readDouble();
    }
}