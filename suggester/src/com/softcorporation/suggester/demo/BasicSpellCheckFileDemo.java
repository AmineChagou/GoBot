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

import java.io.*;
import java.util.*;

import com.softcorporation.suggester.util.Constants;
import com.softcorporation.suggester.util.SpellCheckConfiguration;
import com.softcorporation.suggester.Suggestion;
import com.softcorporation.suggester.tools.SpellCheck;
import com.softcorporation.suggester.dictionary.BasicDictionary;
import com.softcorporation.suggester.BasicSuggester;

/**
 * Basic Spell Check reading File Demo
 * <p>
 * @version   1.0, 02/02/2005
 * @author    Vadim Permakoff
 */
public class BasicSpellCheckFileDemo
{
  public static void main(String[] args)
  {
    String command;
    String prevCommand = "";
    String word;
    String prevWord = "";
    SpellCheck spellCheck = null;

    try
    {
      long memory0;
      long memory1;

      // get start times
      long time0;
      long time1;

      String workingDir = "\\Projects\\suggester\\";
      String dictFileName = "file://" + workingDir + "dict\\en\\english.jar";

      File inFile = new File(workingDir + "test\\input.txt");
      File outFile = new File(workingDir + "test\\output.html");

      System.out.println("Loading dictionary ...");
      memory0 = getMemory();
      time0 = System.currentTimeMillis();

      BasicDictionary dictionary = new BasicDictionary(dictFileName);

      time1 = System.currentTimeMillis();
      memory1 = getMemory();
      System.out.println("Done. It took " + (time1 - time0) +
                         " milliseconds. Used memory: " + (memory1 - memory0) +
                         "\n");

      SpellCheckConfiguration configuration = new SpellCheckConfiguration(
          "spellCheck.config");

      BasicSuggester suggester = new BasicSuggester(configuration);
      suggester.attach(dictionary);

      BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(
          System.in, Constants.CHARACTER_SET_ENCODING_DEFAULT));
      while (true)
      {
        System.out.print("\nPlease enter command ('?' - help): ");
        command = keyboardInput.readLine().toLowerCase();

        if ("?".equals(command))
        {
          System.out.println("\n'?' - print help screen;");
          System.out.println("'q' - exit;");
          System.out.println("'s' - spelling suggestions in file;");
        }
        else if ("q".equals(command))
        {
          break;
        }
        else if ("s".equals(command))
        {
          while (true)
          {
            String text = readInput(inFile);

			System.out.println("\ntext: " + text);

            if (text.trim().length() == 0)
            {
              break;
            }

            time0 = System.currentTimeMillis();
            ArrayList suggestions = null;

            spellCheck = new SpellCheck(configuration);
            spellCheck.setSuggester(suggester);
            spellCheck.setSuggestionLimit(5);

            spellCheck.setText(text, Constants.DOC_TYPE_TEXT, "ru");
//            spellCheck.setText(text);
            spellCheck.check();
            while (spellCheck.hasMisspelt())
            {
              String misspeltWord = spellCheck.getMisspelt();
              String misspeltText = spellCheck.getMisspeltText(5, "<b>", "</b>",
                  5);
              System.out.println("Misspelt text: " + misspeltText);
              System.out.println("Misspelt word: " + misspeltWord);

              suggestions = spellCheck.getSuggestions();

              System.out.println("Suggestions: ");
              for (int j = 0; j < suggestions.size(); j++)
              {
                Suggestion suggestion = (Suggestion) suggestions.get(j);
                System.out.println(j + ": " + suggestion.word);
              }
              System.out.print("Select suggestion (CR - next, q - quit): ");
              command = keyboardInput.readLine().toLowerCase();

              if (command.length() != 0)
              {
                if ("q".equals(command))
                {
                  break;
                }
                int k = 0;
                String selectedWord;
                try
                {
                  k = Integer.parseInt(command);
                  Suggestion suggestion = (Suggestion) suggestions.get(k);
                  selectedWord = suggestion.word;
                }
                catch (Exception ex)
                {
                  System.out.print("Invalid command!");
                  continue;
                }
                spellCheck.change(selectedWord);
              }
              spellCheck.checkNext();
            }
            text = spellCheck.getText();

            time1 = System.currentTimeMillis();
            System.out.println("Done. It took " + ( (float) (time1 - time0)) +
                               " milliseconds.\n");
            break;
          }
        }
        else
        {
          System.out.println("\nUnknown command. Enter '?' for help");
        }

      }
      System.out.println("\nExit.");
    }
    catch (Exception e)
    {
//      if (spellCheck != null)
//      {
//      int i = spellCheck.getPosition();
//      System.out.println("Position: " + i);
//      System.out.println("Word: " + spellCheck.getDocument().toString(i));
//      }
      System.out.println("Error: " + e);
    }
  }

  // Note, this is not valid method to measure memory size, but it can give you some estimate
  static long getMemory()
  {
    try
    {
      System.gc();
      System.gc();
      Thread.yield();
      System.gc();
      System.gc();
      Thread.sleep(100);
      System.gc();
      System.gc();
    }
    catch (Exception e)
    {}
//    System.out.println("TotalMemory=" + Runtime.getRuntime().totalMemory());
//    System.out.println("FreeMemory=" + Runtime.getRuntime().freeMemory());
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }

  static String readInput(File inFile) throws Exception
  {
    FileInputStream inStream = new FileInputStream(inFile);
    BufferedReader br = new BufferedReader(new InputStreamReader(inStream,
        "UTF-8"));
    String inputLine;
    StringBuffer sb = new StringBuffer();
    while ( (inputLine = br.readLine()) != null)
    {
      sb.append(inputLine);
      sb.append("\n");
    }
    return sb.toString();
  }

  static void writeOutput(File outFile, String text) throws Exception
  {
    FileOutputStream outStream = new FileOutputStream(outFile.getAbsolutePath(), true);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream,
        "UTF-8"));
    bw.write(text);
    bw.close();
    outStream.close();
  }

}
