public class Book {
    public String name;
    public String author;
    public int price;
    public String type;
    public boolean isBorrowed;
    public Book(String name,String author,int price,String type){
        this.name=name;
        this.author=author;
        this.price=price;
        this.type=type;
    }
    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ((isBorrowed==true) ? "，已经被借出":"，未借出")+
                '}';
    }
}

public class BookList {
    public Book[] books=new Book[10];
    public int size;//有效数据个数

    public BookList(){
        books[0]=new Book("三国演义","罗贯中",13,"小说");
        books[1]=new Book("西游记","吴承恩",6,"小说");
        books[2]=new Book("水浒传","施耐庵",17,"小说");
        this.size=3;
    }
    public void setBook(int pos,Book book){
        this.books[pos]=book;
    }
    public Book getBook(int pos){
        return books[pos];
    }
    public void setSize(int size){
        this.size=size;
    }
    public int getSize(){
        return this.size;
    }
}


public class AddOperation implements IOperation{
    @Override
    public void work(BookList bookList) {
        System.out.println("添加书籍");
        System.out.println("请输入图书的名字：");
        String name = scanner.next();
        System.out.println("请输入图书的作者");
        String author = scanner.next();
        System.out.println("请输入图书的价格");
        int price = scanner.nextInt();
        System.out.println("请输入图书的类型");
        String type = scanner.next();
        Book book = new Book(name,author,price,type);
        //没有考虑满的情况
        int size = bookList.getSize();
        bookList.setBook(size,book);
        bookList.setSize(size+1);
        System.out.println("添加书籍成功！");
    }
}


public class BorrowOperation implements IOperation{
    @Override
    public void work(BookList booklist) {
        System.out.println("输入需要借阅书籍的名称");
        String name = scanner.nextLine();
        int i=0;
        for(;i<booklist.getSize();i++){
            if(booklist.getBook(i).name.equals(name)){
                break;
            }
        }
        if(i==booklist.getSize()){
            System.out.println("没有此书");
            return;
        }
        if(booklist.getBook(i).isBorrowed){
            System.out.println("此书已被借出");
        }else{
            booklist.getBook(i).isBorrowed=false;
            System.out.println("借阅成功");
        }
    }
}


public class DelOperation implements IOperation{

    @Override
    public void work(BookList booklist) {
        System.out.println("输入需要删除书籍的名称");
        String name = scanner.next();
        int i=0;
        for(;i<booklist.getSize();i++){
            if(booklist.getBook(i).name.equals(name)){
                break;
            }
        }
        if(i==booklist.getSize()){
            System.out.println("没有此书");
            return;
        }
        for(;i<booklist.getSize()-1;i++){
            Book next=booklist.getBook(i+1);
            booklist.setBook(i,next);
        }

    }
}


public class DisplayOperation implements IOperation{
    @Override
    public void work(BookList bookList) {
        for (int i = 0; i < bookList.getSize(); i++) {
            System.out.println(bookList.getBook(i));
        }
    }
}

public class ExitOperation implements IOperation{
    @Override
    public void work(BookList bookList) {
        System.out.println("byebye!");
        System.exit(0);
    }
}


public class FindOperation implements IOperation{
    @Override
    public void work(BookList bookList) {
        System.out.println("输入需要查找书籍的名称");
        String name = scanner.next();
        int i = 0;
        for (; i < bookList.getSize(); i++) {
            if(bookList.getBook(i).name.equals(name)) {
                break;
            }
        }
        if(i >= bookList.getSize()) {
            System.out.println("没有此书");
            return;
        }
        System.out.println("书籍查找成功");
    }
}


public interface IOperation {
     Scanner scanner = new Scanner(System.in);
     void work(BookList bookList);
 }
 
 public class ReturnOperation implements IOperation{
    @Override
    public void work(BookList booklist) {
        System.out.println("输入需要删除书籍的名称");
        String name = scanner.next();
        int i = 0;
        for (; i < booklist.getSize(); i++) {
            if(booklist.getBook(i).name.equals(name)) {
                break;
            }
        }
        if(i >= booklist.getSize()) {
            System.out.println("此书未被借出");
        }else{
            booklist.getBook(i).isBorrowed=false;
        }
    }
}


public class Admin extends User{
    public Admin(String name){
        this.name=name;
        this.operations=new IOperation[]{
                new ExitOperation(),
                new FindOperation(),
                new AddOperation(),
                new DelOperation(),
                new DisplayOperation(),
        };
    }
    @Override
    public int menu() {
        System.out.println("=============================");
        System.out.println("Hello " + this.name + ", 欢迎使用图书管理系统!");
        System.out.println("1. 查找图书");
        System.out.println("2. 新增图书");
        System.out.println("3. 删除图书");
        System.out.println("4. 显示所有图书");
        System.out.println("0. 退出系统");
        System.out.println("=============================");
        System.out.println("请输入您的选择: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}


public class NormalUser extends User {
    public NormalUser(String name){
        this.name=name;
        this.operations=new IOperation[]{
                new ExitOperation(),
                new FindOperation(),
                new BorrowOperation(),
                new ReturnOperation(),
                new DisplayOperation(),
        };
    }
    @Override
    public int menu() {
        System.out.println("=============================");
        System.out.println("Hello " + this.name + ", 欢迎使用图书管理系统!");
        System.out.println("1. 查找图书");
        System.out.println("2. 借阅图书");
        System.out.println("3. 归还图书");
        System.out.println("4. 打印图书");
        System.out.println("0. 退出系统");
        System.out.println("=============================");
        System.out.println("请输入您的选择: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}


public  abstract class User {
    protected String name;
    protected IOperation[] operations;

    public abstract int menu();

    public void doOperation(int choice, BookList bookList) {
        operations[choice].work(bookList);

    }
}

public class Main{
    public static User login(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入你的名字：");
        String name=sc.nextLine();
        System.out.println("请输入你的身份(1-->管理者    2-->学生)：");
        int choice=sc.nextInt();
        if(choice==0){
            return new NormalUser(name);
        }else{
            return new Admin(name);
        }

    }
    public static void main(String[] args) {
        BookList bookList=new BookList();
        User user=login();
        while(true){
            int choice=user.menu();
            user.doOperation(choice,bookList);
        }
    }
}
