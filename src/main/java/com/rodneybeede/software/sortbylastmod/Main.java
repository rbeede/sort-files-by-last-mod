package com.rodneybeede.software.sortbylastmod;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class Main {
	

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		if(null == args || args.length == 0) {
			System.err.println("Usage:  java -jar program.jar <source directory>");
			System.err.println("\t" + "Output will be in newly createde 'sortedDirectory'");
			System.err.println();
			System.exit(255);
		}


		final File directory = new File(args[0]);
		
		final File newDirectoryBase = new File(directory.getParentFile(), "sortedDirectory");
		
		
		sortDirectory(directory, newDirectoryBase);

	}
	
	
	private static void sortDirectory(final File directoryToSort, final File newBaseDirectory) {
		for(final File dirEntry : directoryToSort.listFiles()) {
			System.out.println("Looking at " + dirEntry.getAbsolutePath());
			
			if(dirEntry.isDirectory()) {
				sortDirectory(dirEntry, newBaseDirectory);
			} else {
				final Date lastModified = new Date(dirEntry.lastModified());
				
				System.out.print('\t');
				System.out.println("Last modified:  " + lastModified.toString());
				
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(lastModified);
				
				File newEntryDirectory = new File(newBaseDirectory, Integer.toString(calendar.get(Calendar.YEAR)));
				newEntryDirectory = new File(newEntryDirectory, String.format("%02d", calendar.get(Calendar.MONTH)+1));
				newEntryDirectory = new File(newEntryDirectory, String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
				
			
				final File newEntryFile = new File(newEntryDirectory, dirEntry.getName());

				System.out.print('\t');
				System.out.println("Moving to " + newEntryFile);
				
				newEntryDirectory.mkdirs();  // May be redundant so no error check now (but later)
				
				// Move the file
				if(!dirEntry.renameTo(newEntryFile)) {
					System.err.println("FAILED TO DO RENAME");
					System.exit(254);
				}
				
			}
		}
	}

}
