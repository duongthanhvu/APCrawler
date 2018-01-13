/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apcrawler;

/**
 *
 * @author vudtpk0074
 */
public class APCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        ContentScraper scraper = new ContentScraper("1odg4ajfhrp4tusipm9n7rppr2"); //Đăng nhập vào AP rồi copy và paste cookie PHPSESSID vào đây
        XLSXWriter xlsxWriter = new XLSXWriter();
        for(int i = 6940; i <= 7061; i++){ //dải id tương ứng với kỳ Fall 2017 là 6940 đến 7061 
            MarkSheet markSheet = scraper.scrapContent(i);
            if(markSheet != null){
                System.out.println("Scrap " + i + " done!");
                xlsxWriter.addSheet(markSheet);
            }else{
                System.out.println(i + " null!"); //proposal: Lưu lại các giá trị id valid để sử dụng cho lần sau
            }
        }
        xlsxWriter.exportXLSX();
    }
}
