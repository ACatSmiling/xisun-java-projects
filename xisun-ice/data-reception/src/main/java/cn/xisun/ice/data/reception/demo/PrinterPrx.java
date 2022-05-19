// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.5
//
// <auto-generated>
//
// Generated from file `Printer.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package cn.xisun.ice.data.reception.demo;

public interface PrinterPrx extends Ice.ObjectPrx {
    public void printString(String s);

    public void printString(String s, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_printString(String s);

    public Ice.AsyncResult begin_printString(String s, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_printString(String s, Ice.Callback __cb);

    public Ice.AsyncResult begin_printString(String s, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_printString(String s, Callback_Printer_printString __cb);

    public Ice.AsyncResult begin_printString(String s, java.util.Map<String, String> __ctx, Callback_Printer_printString __cb);

    public Ice.AsyncResult begin_printString(String s,
                                             IceInternal.Functional_VoidCallback __responseCb,
                                             IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_printString(String s,
                                             IceInternal.Functional_VoidCallback __responseCb,
                                             IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                             IceInternal.Functional_BoolCallback __sentCb);

    public Ice.AsyncResult begin_printString(String s,
                                             java.util.Map<String, String> __ctx,
                                             IceInternal.Functional_VoidCallback __responseCb,
                                             IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb);

    public Ice.AsyncResult begin_printString(String s,
                                             java.util.Map<String, String> __ctx,
                                             IceInternal.Functional_VoidCallback __responseCb,
                                             IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb,
                                             IceInternal.Functional_BoolCallback __sentCb);

    public void end_printString(Ice.AsyncResult __result);
}
