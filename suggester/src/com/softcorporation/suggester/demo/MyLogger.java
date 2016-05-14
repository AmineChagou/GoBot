/**
 * Copyright (c) 2005 SoftCorporation LLC. All rights reserved.
 *
 * The Software License, Version 1.0
 *
 * SoftCorporation LLC. grants you ("Licensee") a non-exclusive, royalty free,
 * license to use, modify and redistribute this software in source and binary
 * code form, provided that the following conditions are met:
 *
 * 1. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        SoftCorporation LLC. (http://www.softcorporation.com)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 2. The names "Suggester" and "SoftCorporation" must not be used to
 *    promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    info@softcorporation.com.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED.
 * IN NO EVENT SHALL THE SOFTCORPORATION BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION).
 *
 */
package com.softcorporation.suggester.demo;

import java.util.*;

import com.softcorporation.util.Logger;

/**
 * <code>Logger</code> - logs messages.
 * By default logging goes to Standard and Error outputs.
 * This is just an example to help you to write your own logger 
 * extending com.softcorporation.util.Logger.
 * <p>
 * @version   1.0, 02/02/2005
 * @author    Vadim Permakoff
 */
public class MyLogger extends Logger
{
  /**
   * Logs error message. By default logging goes to error output.
   * @param error message
   */
  public void writeError(String message)
  {
    System.err.println(new Date() + "\tMY_ERROR\t" + message);
  }

  /**
   * Logs info message. By default logging goes to standard output.
   * @param info message
   */
  public void writeInfo(String message)
  {
    System.out.println(new Date() + "\tMY_INFO\t" + message);
  }

  /**
   * Logs debug message. By default logging goes to standard output.
   * @param info message
   */
  public void writeDebug(String message)
  {
    System.out.println(new Date() + "\tMY_DEBUG\t" + message);
  }

}
// end of class Logger
