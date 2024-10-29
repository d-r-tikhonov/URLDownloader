package ru.ncedu.tikhonov.URLDownloader;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String  pdfURL         = null;
        String  savePath       = URLDownloader.getDefaultFilePath();
        boolean openFirstImage = false;
        int     dpi            = 300;
        int     startPage      = 1;
        int     endPage        = Integer.MAX_VALUE;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("--open")) {
                openFirstImage = true;
            } else if (pdfURL == null) {
                pdfURL = arg;
            } else if (arg.matches("\\d+")) {
                dpi = Integer.parseInt(arg);
            } else if (arg.matches("\\d+-\\d+")) {
                String[] pages = arg.split("-");

                startPage = Integer.parseInt(pages[0]);
                endPage   = Integer.parseInt(pages[1]);
            } else {
                savePath = arg;
            }
        }

        if (pdfURL == null) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String pdfFileName = URLDownloader.downloadPDF(pdfURL, savePath);
        URLDownloader.convertPdfToPNG(pdfFileName, dpi, startPage, endPage);
        if (openFirstImage) {
            URLDownloader.openFirstImage(pdfFileName);
        }
    }
}