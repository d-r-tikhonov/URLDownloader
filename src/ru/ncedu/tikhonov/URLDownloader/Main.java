package ru.ncedu.tikhonov.URLDownloader;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String pdfURL   = args[0];
        String savePath = args.length > 1 ? args[1] : URLDownloader.getDefaultFilePath();

        String pdfFileName = URLDownloader.downloadPDF(pdfURL, savePath);
        URLDownloader.convertPdfToPNG(pdfFileName);
    }
}