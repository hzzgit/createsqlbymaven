package tools;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


//这个类是用来复制内容到电脑的剪切板中
public class ClipBoardUtil {
	
	
	 public static void setSysClipboardText(String writeMe) {  
	        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
	        Transferable tText = new StringSelection(writeMe);  
	        clip.setContents(tText, null);  
	    } 
	 
}
