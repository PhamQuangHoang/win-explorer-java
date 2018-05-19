 Link video hướng dẫn sử dụng :
    https://youtu.be/3HkkwRo0BRI

Tên SV : Phạm Quang Hoàng 

MÃ SV : 17it059 

ĐỀ số 10 :   

Câu 1 : FileExplore ;

Câu 2 : Chức năng rename File; 

Chức năng làm thêm  Copy File ;

******************************************************************************
# Hướng dẫn sử dụng : 
 Chức năng rename : 
     
     Chọn File cần rename trên table . 
     
     Click vào button rename và nhập tên mới . 
     
     click Ok or ấn Enter . Nếu báo Success thì rename hoàn tất .
     
  Chức năng phụ Copy : 
     
     Chọn File cần copy trên table . 
     
     Click vào button copy sẽ hiện lên thông báo kèm với tên File.
     
     Chọn thư mục cần copy và Click Paste . 
     
 Cách 2 chức năng trên chạy được trình bày ở cuối trang :D.     
******************************************************************************
 # CÁCH CODE CHẠY :        

  ## Các hàm sử dụng và chức năng :

DefaultTableModel CreateTableData(DefaultTableModel tb, String folder)
 
  Trả về kiểu DefaultTablemode dùng để fill dữ liệu vào bảng .

******************************************************************************

String FileNode(File file) 

Hàm trả về giá trị là Tên của file hoặc đường dẫn của file  . 
******************************************************************************
createChildren(File fileRoot, DefaultMutableTreeNode node)
 
Sử dụng để tạo ra các node con cho cây (Jtree) 

Tương tự như hàm CreateTableData thì hàm này dùng đễ fill dữ liệu cho tree
 tạo ra các node con cho tree . 
Sau khi duyệt qua toàn bộ dữ liệu của File thì thực hiện gọi đệ quy để tiếp tục 
tạo ra các nhánh tiếp theo của cây . 
********************************************************************************
Hàm getTreepath(Treepath path) 

Đây là hàm sử lý String để lấy được đường dẫn của cây . 
Hỗ trợ cho việc lấy đường dẫn đúng của thư mục .
******************************************************************************
copyFile(Path source, Path destination) throws IOException

dùng để copyfile (chức năng thêm )
******************************************************************************
# Giao diện: 
/*Vì lý do ổ C có nhiều dữ liệu quan trọng nên em không nạp ổ C vào tree. 
 
gồm 4 phần :

                      top => Show path of File . 
                      left => Tree .                       
                      center=> table .
                      bottom=> label của name , size , type và các button chức năng .

# Chạy chương trình  :

First :  Tạo ra các cành cho jtree bằng hàm createChildren(gốc cây , Đường dẫn ); 

 add cây con cho tree , sử dụng DefaultTreemodel là dữ liệu đễ thêm vào jtree . 
 
treemodel = new DefaultTreeModel(root);

//root là gốc của cây có kiểu DefautlMuTableTreeNode.

//các node là cây con được add vào root

Rồi thêm dữ liệu cho cây 

tree.setModel(treemodel);
 
Bắt sự kiện valueChanged cho tree 

DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
*/ lấy giá trị được chọn khi click từ cây  ;

TreePath treepath = e.getPath();
*/ dùng TreePath đễ lấy đường dẫn từ cây . 

txtpath.setText(getTreepath(treepath));
*/ dùng hàm getTreepath đễ xử lý String lấy được đường dẫn đúng . 

VD: đường dẫn gốc khi lấy  từ treepath [this PC:,][D:\,]

    thì sau khi qua hàm này sẽ trờ thành this PC:\\D    (đường dẫn đúng)

và set đường dẫn đúng vào hàm CreateTabledata để load thư mục cho table . 


Bắt sự kiện mousePressed cho table : 

int row = table.getSelectedRow();
*/lấy hàng được chọn

String name = table.getModel().getValueAt(row, 0).toString();
 */ lấy giá trị được chọn tại vị trí click .
 
Sử dụng giá trị "name " vừa lấy cùng với path hiện tại đề load table mới

Bắt sự kiện double click bằng getClickCout để mở folder mới ở trên table .

******************************************************************************

# CHỨC NĂNG CHÍNH : RENAME 

 Sử dụng File.renameto(File("newpath"); của thử viện java.io.*;
  
   Bắt event mouseClicked cho btnRENAME ; 
   
step 1 : 

         Lấy tên mới từ người sử dụng bằng box của joptionpane .
         
step 2 : 

          Lấy đường dẫn hiện tại của file để sử dụng cho việc rename tại txtPath ; 
          
          Dùng đường dẫn đó cộng chuỗi với tên củ của file ta nhận được 1 path của file củ

          Tương tự thì ta cũng thực hiện cộng chuỗi với tên mới mà người dùng đã nhập ở step 1 
          có được đường dẫn và tên mới .
          
           Cuối cùng thực hiện đổi tên bằng File.renameto(File("newpath)
                                               ^             ^          
                                               ||            ||
                        File chứa đường dẫn và tên củ ||  File chứa đường dẫn và tên mới .

******************************************************************************

# CHỨC NĂNG PHỤ : COPYFILE 

Dùng Files.copy(path SrcFile , path DesFile);

Hàm được sử dụng để copy với 2 đối số là đường dẫn đến thư mục chứa file và đường dẫn mới đến
thư mục cần copy .

vd :  

         sourcepath :   D:\newfolder:\File.java  
         
         DesPath :  D:\File.java 
         
         Thì File.java sẽ được copy từ sourcepath đến desPath (từ thư mục newfolder -> ổ D )

Bắt sự kiện mouseClicked  cho btnCopy ;

Step 1 : Lấy Đường dẫn hiện tại của File và cộng chuỗi là tên File để có được đường dẫn hiện tại . 

Step 2 : Trỏ đến đường dẫn mới , lưu đường dẫn mới vào Paths và cộng chuổi vs tên File để có được 
        đường dẫn đến thư mục muốn copy đến

Step 3  : Thức hiện copy với 2 đường dẫn đã lấy được ở 2 bước trước bằng hàm Copy đã nói trên . 




             ******************************************Finish********************************

 
           
 

