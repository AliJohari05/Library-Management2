import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.Integer;
import java.time.format.DateTimeFormatterBuilder;

public class Main {
    public static long calculateDateTimeDifference(String startDate, String startTime, String endDate, String endTime) {
        if (startDate != null && startTime != null && endDate != null && endTime != null &&
                startTime.matches("\\d{2}:\\d{2}") && endTime.matches("\\d{2}:\\d{2}")) {

            DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-d"))
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-d"))
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-dd"))
                    .toFormatter();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDateTime startDateTime = LocalDateTime.of(
                    LocalDate.parse(startDate, dateFormatter),
                    LocalTime.parse(startTime, timeFormatter)
            );

            LocalDateTime endDateTime = LocalDateTime.of(
                    LocalDate.parse(endDate, dateFormatter),
                    LocalTime.parse(endTime, timeFormatter)
            );

            long hoursDifference = Duration.between(endDateTime, startDateTime).toHours();
            return Math.max(0, hoursDifference);
        }
        return 0;
    }
    public  static boolean checkTimeOverlap(String date1, String startTime1, String endTime1, String date2, String startTime2, String endTime2) {
        if (date1 != null && startTime1 != null && endTime1 != null && date2 != null && startTime2 != null && endTime2 != null &&
                startTime1.matches("\\d{2}:\\d{2}") && endTime1.matches("\\d{2}:\\d{2}") &&
                startTime2.matches("\\d{2}:\\d{2}") && endTime2.matches("\\d{2}:\\d{2}") &&
                date1.matches("\\d{4}-\\d{2}-\\d{2}") && date2.matches("\\d{4}-\\d{2}-\\d{2}")) {

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate localDate1 = LocalDate.parse(date1, dateFormatter);
            LocalTime startLocalTime1 = LocalTime.parse(startTime1, timeFormatter);
            LocalTime endLocalTime1 = LocalTime.parse(endTime1, timeFormatter);
            LocalDateTime startDateTime1 = LocalDateTime.of(localDate1, startLocalTime1);
            LocalDateTime endDateTime1 = LocalDateTime.of(localDate1, endLocalTime1);

            LocalDate localDate2 = LocalDate.parse(date2, dateFormatter);
            LocalTime startLocalTime2 = LocalTime.parse(startTime2, timeFormatter);
            LocalTime endLocalTime2 = LocalTime.parse(endTime2, timeFormatter);
            LocalDateTime startDateTime2 = LocalDateTime.of(localDate2, startLocalTime2);
            LocalDateTime endDateTime2 = LocalDateTime.of(localDate2, endLocalTime2);

            return !(endDateTime1.isBefore(startDateTime2) || endDateTime2.isBefore(startDateTime1));
        }
        return false;
    }
    public static void main(String[] args) {
        long penalty = 0 ;
        ArrayList<Library> libraries = new ArrayList<>();
        ArrayList<Users> users = new ArrayList<>();
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("null", "null", "null"));
        Scanner input = new Scanner(System.in);
        boolean finish = false;
        Admin admin = new Admin();
        users.add(admin);
        while (!finish) {
            StringBuilder order = new StringBuilder();
            order.append(input.nextLine());
            if (order.toString().equals("finish")) {
                finish = true;
            } else if (order.toString().startsWith("add-library#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagLibrary = 0;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])){
                        flagLibrary = 1;
                    }
                }

                if(flagUser == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(flagLibrary == 1) {
                    System.out.println("duplicate-id");
                }
                else{
                    int numOfTable = Integer.parseInt(arr[5]);
                    libraries.add(new Library(arr[2],arr[3],arr[4],numOfTable,arr[6]));
                    System.out.println("success");
                }

            }else if (order.toString().startsWith("add-category#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagCategory = 0;
                for(Category cat : categories) {
                    if(cat.getId().equals(arr[2])){
                        flagCategory = 1;
                        break;
                    }
                }
                int flagCategoryFather = 0;
                for(Category cat : categories) {
                    if(cat.getId().equals(arr[4])){
                        flagCategoryFather = 1;
                        break;
                    }
                }

                if(flagUser == 0 ) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(flagCategory == 1) {
                    System.out.println("duplicate-id");
                }
                else if(flagCategoryFather == 0){
                    System.out.println("not-found");
                }
                else{
                    categories.add(new Category(arr[2],arr[3],arr[4]));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("add-student#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagStudent = 0;
                for(Users user : users) {
                    if(user.getId().equals(arr[2])) {
                        flagStudent = 1;
                        break;
                    }
                }
                if(flagUser == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(flagStudent == 1) {
                    System.out.println("duplicate-id");
                }
                else{
                    users.add(new Student(arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8]));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("add-staff#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagStaff = 0;
                for(Users user : users) {
                    if(user.getId().equals(arr[2])) {
                        flagStaff = 1;
                        break;
                    }
                }
                if(flagUser == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(flagStaff == 1) {
                    System.out.println("duplicate-id");
                }
                else{
                    if(arr[9].equals("staff")) {
                        users.add(new Staff(arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8]));
                    }else{
                        users.add(new Professor(arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8]));
                    }
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("add-manager#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagLibrary = 0 ;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[9])) {
                        flagLibrary = 1;
                        break;
                    }
                }
                int flagManager = 0;
                for(Users user : users) {
                    if(user.getId().equals(arr[2])) {
                        flagManager = 1;
                        break;
                    }
                }
                if(flagUser == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])){
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(flagManager == 1) {
                    System.out.println("duplicate-id");
                }
                else{
                    users.add(new Manager(arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8],arr[9]));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("remove-user#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagRemoveUser = 0;
                Users removeUser = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[2])){
                        flagRemoveUser = 1;
                        removeUser = user;
                        break;
                    }
                }

                if(flagUser == 0 || flagRemoveUser == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else if(removeUser.getIsNotReturn() || !removeUser.getBorrow().isEmpty()){
                    System.out.println("not-allowed");
                }
                else{
                    users.remove(removeUser);
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("add-book#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                Manager itManager = null;
                if(it instanceof Manager) {
                    flagManager = 1;
                    itManager = (Manager)it;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[9])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagCategory = 0;
                Category myCategory = null;
                for(Category category : categories) {
                    if(category.getId().equals(arr[8])) {
                        flagCategory = 1;
                        myCategory = category;
                        break;
                    }
                }
                int flagBook = 0;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[2])) {
                            flagBook = 1;
                            break;
                        }
                    }

                }
                if(flagUser == 0 || flagLibrary == 0 || flagCategory == 0) {
                    System.out.println("not-found");
                }
                else if(flagManager == 0 || !itManager.getIdLibrary().equals(arr[9])) {
                    System.out.println("permission-denied");
                }
                else if(flagBook == 1){
                    System.out.println("duplicate-id");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else{
                    int numOfBooks = Integer.parseInt(arr[7]);
                    myLib.getResources().add(new Book(arr[2],arr[3],arr[4],arr[5],arr[6],numOfBooks,myCategory));
                    System.out.println("success");
                }

            }
            else if (order.toString().startsWith("add-thesis#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                Manager itManager = null;
                if(it instanceof Manager) {
                    flagManager = 1;
                    itManager = (Manager)it;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[8])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagCategory = 0;
                Category myCategory = null;
                for(Category category : categories) {
                    if(category.getId().equals(arr[7])) {
                        flagCategory = 1;
                        myCategory = category;
                        break;
                    }
                }
                int flagThesis = 0;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[2])) {
                            flagThesis = 1;
                            break;
                        }
                    }

                }
                if(flagUser == 0 || flagLibrary == 0 || flagCategory == 0) {
                    System.out.println("not-found");
                }
                else if(flagManager == 0 || !itManager.getIdLibrary().equals(arr[8])) {
                    System.out.println("permission-denied");
                }
                else if(flagThesis == 1){
                    System.out.println("duplicate-id");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else{
                    myLib.getResources().add(new Thesis(arr[2],arr[3],arr[4],arr[5],arr[6],myCategory));
                    System.out.println("success");
                }

            }
            else if (order.toString().startsWith("add-ganjineh-book#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                Manager itManager = null;
                if(it instanceof Manager) {
                    flagManager = 1;
                    itManager = (Manager)it;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[9])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagCategory = 0;
                Category myCategory = null;
                for(Category category : categories) {
                    if(category.getId().equals(arr[8])) {
                        flagCategory = 1;
                        myCategory = category;
                        break;
                    }
                }
                int flagGhanjineBook = 0;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[2])) {
                            flagGhanjineBook = 1;
                            break;
                        }
                    }

                }
                if(flagUser == 0 || flagLibrary == 0 || flagCategory == 0) {
                    System.out.println("not-found");
                }
                else if(flagManager == 0 || !itManager.getIdLibrary().equals(arr[9])) {
                    System.out.println("permission-denied");
                }
                else if(flagGhanjineBook == 1){
                    System.out.println("duplicate-id");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else{
                    myLib.getResources().add(new GhanjinehBook(arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],myCategory));
                    System.out.println("success");
                }

            }
            else if (order.toString().startsWith("add-selling-book#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                Manager itManager = null;
                if(it instanceof Manager) {
                    flagManager = 1;
                    itManager = (Manager)it;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[11])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagCategory = 0;
                Category myCategory = null;
                for(Category category : categories) {
                    if(category.getId().equals(arr[10])) {
                        flagCategory = 1;
                        myCategory = category;
                        break;
                    }
                }
                int flagSellingBook = 0;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[2])) {
                            flagSellingBook = 1;
                            break;
                        }
                    }

                }
                if(flagUser == 0 || flagLibrary == 0 || flagCategory == 0) {
                    System.out.println("not-found");
                }
                else if(flagManager == 0 || !itManager.getIdLibrary().equals(arr[11])) {
                    System.out.println("permission-denied");
                }
                else if(flagSellingBook == 1){
                    System.out.println("duplicate-id");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else{
                    int numOfBooks = Integer.parseInt(arr[7]);
                    long price = Long.parseLong(arr[8]);
                    int discount = Integer.parseInt(arr[9]);
                    myLib.getResources().add(new SellingBook(arr[2],arr[3],arr[4],arr[5],arr[6],numOfBooks,price,discount,myCategory));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("remove-resource#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                Manager itManager = null;
                if(it instanceof Manager) {
                    flagManager = 1;
                    itManager = (Manager)it;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[3])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                ReadingResource myResource = null;
                Book myBook = null;
                Thesis myThesis = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[2])) {
                            flagResource = 1;
                            myResource = resource;
                            if(myResource instanceof Thesis) {
                                myThesis = (Thesis)myResource;
                            }
                            if(myResource instanceof Book) {
                                myBook = (Book)myResource;
                            }
                            break;
                        }
                    }

                }

                if(flagUser == 0 || flagLibrary == 0 ) {
                    System.out.println("not-found");
                }
                else if(flagManager == 0 || !itManager.getIdLibrary().equals(arr[3])) {
                    System.out.println("permission-denied");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(flagResource == 0){
                    System.out.println("not-found");
                }
                else if((myBook != null && myBook.getNumberOfReadings() != myBook.getCteNumber()) || (myThesis != null && myThesis.getNumberOfReadings() != 1)) {
                    System.out.println("not-allowed");
                }
                else{
                    myLib.getResources().remove(myResource);
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("borrow#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for(Users user : users) {
                    if(user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }

                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                int flagBookOrThesis = 0;
                Book myBook = null;
                Thesis myThesis = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[3])) {
                            flagResource = 1;
                            if(resource instanceof Book || resource instanceof Thesis) {
                                flagBookOrThesis = 1;
                                if(resource instanceof Book) {
                                    myBook = (Book)resource;
                                }
                                else {
                                    myThesis = (Thesis)resource;
                                }
                            }
                            break;
                        }
                    }
                }
                int flagDuplicate = 0;
                if(it != null && flagBookOrThesis == 1) {
                    for(Borrower borrow : it.getBorrow()){
                        if(borrow.getIdBook().equals(arr[3]) && borrow.getIdLibrary().equals(arr[2])) {
                            flagDuplicate = 1;
                            break;
                        }
                    }
                }

                if(flagUser == 0 || flagLibrary == 0 || flagResource == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }

                else if(flagDuplicate == 1 || (myThesis != null && myThesis.getNumberOfReadings() == 0) || (myBook != null && myBook.getNumberOfReadings() == 0)|| flagBookOrThesis == 0 || it.getIsNotReturn() || (it != null && it.getLimitedNumber() == 0)) {
                    System.out.println("not-allowed");
                }
                else{
                    int BookOrThesis = 0;
                    if(myThesis != null ){
                        BookOrThesis = -1;
                        myThesis.setNumberOfReadings(myThesis.getNumberOfReadings() - 1);
                    }
                    else if(myBook != null ){
                        BookOrThesis = 1;
                        myBook.setNumberOfReadings(myBook.getNumberOfReadings() - 1);
                    }
                    it.setLimitedNumber(it.getLimitedNumber() - 1);
                    it.addBorrow(new Borrower(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],BookOrThesis));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("return#")) {
                double fine = 0;
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                Student itStudent = null;
                Manager itManager = null;
                Staff itStaff = null;
                Professor itProf = null;
                if(it != null) {
                    if(it instanceof Student) {
                        itStudent = (Student)it;
                    }
                    else if(it instanceof Manager) {
                        itManager = (Manager)it;
                    }
                    else if(it instanceof Staff){
                        itStaff = (Staff)it;
                    }
                    else if(it instanceof Professor){
                        itProf = (Professor)it;
                    }
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                int flagBookOrThesis = 0;
                Book myBook = null;
                Thesis myThesis = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[3])) {
                            flagResource = 1;
                            if(resource instanceof Book || resource instanceof Thesis) {
                                flagBookOrThesis = 1;
                                if(resource instanceof Book) {
                                    myBook = (Book)resource;
                                }
                                else {
                                    myThesis = (Thesis)resource;
                                }
                            }
                            break;
                        }
                    }
                }
                int flagBorrow = 0 ;
                Borrower myBorrow = null;
                if(it != null && flagBookOrThesis == 1) {
                    for(Borrower borrow : it.getBorrow()){
                        if(borrow.getIdBook().equals(arr[3]) && borrow.getIdLibrary().equals(arr[2])) {
                            flagBorrow = 1;
                            myBorrow = borrow;
                            break;
                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0 || flagResource == 0 || flagBorrow == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else{
                    //Penalty calculation
                    long outputCalculateDateTimeDifference = calculateDateTimeDifference(arr[4], arr[5], myBorrow.getDateOfBorrow(), myBorrow.getClockOfBorrow());
                    if(itStudent != null){
                        if(myThesis != null){
                            fine = (outputCalculateDateTimeDifference - 7 * 24) * 50;
                        }
                        if(myBook != null){
                            fine = (outputCalculateDateTimeDifference - 10 * 24) * 50;
                        }
                    }
                    else{
                        if(myThesis != null){
                            fine = (outputCalculateDateTimeDifference - 10 * 24) * 100;
                        }
                        if(myBook != null){
                            fine = (outputCalculateDateTimeDifference - 14 * 24) * 100;
                        }
                    }
                    if(myThesis != null){
                        myThesis.setNumberOfReadings(myThesis.getNumberOfReadings() + 1);
                        myThesis.setNumberOfDayBorrowed(myThesis.getNumberOfDayBorrowed() + (long)Math.ceil((double) outputCalculateDateTimeDifference /24));
                        myThesis.setNumberBorrowed(myThesis.getNumberBorrowed() + 1);

                    }
                    if(myBook != null){
                        myBook.setNumberOfReadings(myBook.getNumberOfReadings() + 1);
                        myBook.setNumberOfDayBorrowed(myBook.getNumberOfDayBorrowed() + (long)Math.ceil((double) outputCalculateDateTimeDifference /24));
                        myBook.setNumberBorrowed(myBook.getNumberBorrowed() + 1);

                    }
                    it.setLimitedNumber(it.getLimitedNumber() + 1);
                    it.getBorrow().remove(myBorrow);

                    if(fine > 0){
                        penalty += (long)fine;
                        it.setIsNotReturn(true);
                        System.out.println((long)fine);
                    }
                    else{
                        System.out.println("success");

                    }
                }
            }
            else if (order.toString().startsWith("buy#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagManager = 0 ;
                if(it instanceof Manager) {
                    flagManager = 1;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                int flagBookBuy = 0;
                SellingBook myBook = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[3])) {
                            flagResource = 1;
                            if(resource instanceof SellingBook) {
                                flagBookBuy = 1;
                                myBook = (SellingBook)resource;
                            }
                            break;
                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0 || flagResource == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(flagManager == 1 ){
                    System.out.println("permission-denied");
                }
                else if(flagBookBuy == 0 || (myBook != null && myBook.getNumberOfReadings() == 0) || it.getIsNotReturn()) {
                    System.out.println("not-allowed");
                }
                else{
                    myBook.setNumberOfReadings(myBook.getNumberOfReadings() - 1);
                    myBook.setNumberShellings(myBook.getNumberShellings() + 1);
                    myBook.setTotalShellings(myBook.getTotalShellings() + (long) (myBook.getPrice() - Math.floor((double)myBook.getPrice() * myBook.getDiscount() / 100 )));
                    myLib.setNumSell(myLib.getNumSell() + 1);
                    myLib.setTotalSell(myLib.getTotalSell()+(long) (myBook.getPrice() - Math.floor((double)myBook.getPrice() * myBook.getDiscount() / 100 )));
                    System.out.println("success");
                }
            }
            else if (order.toString().startsWith("read#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagProfessor = 0 ;
                if(it instanceof Professor) {
                    flagProfessor = 1;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                int flagBookGhanjine = 0;
                GhanjinehBook myBook = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[3])) {
                            flagResource = 1;
                            if(resource instanceof GhanjinehBook) {
                                flagBookGhanjine = 1;
                                myBook = (GhanjinehBook) resource;
                            }
                            break;
                        }
                    }
                }
                int checkTime = 0;
                if(myBook != null && myBook.getDate() != null && myBook.getClock() != null) {
                    if(calculateDateTimeDifference(arr[4],arr[5],myBook.getDate(),myBook.getClock()) < 2){
                        checkTime = 1;
                    }
                }
                if(flagUser == 0 || flagLibrary == 0 || flagResource == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(flagProfessor == 0 ){
                    System.out.println("permission-denied");
                }
                else if(flagBookGhanjine == 0 || checkTime == 1 || it.getIsNotReturn()) {
                    System.out.println("not-allowed");
                }
                else{
                    myBook.setDate(arr[4]);
                    myBook.setClock(arr[5]);
                    System.out.println("success");
                }
            }
            else if(order.toString().startsWith("add-comment#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagProfessorOrStudent = 0 ;
                if(it instanceof Professor || it instanceof Student) {
                    flagProfessorOrStudent = 1;
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int flagResource = 0;
                ReadingResource myResource = null;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource.getId().equals(arr[3])) {
                            flagResource = 1;
                            myResource = resource;
                            break;
                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0 || flagResource == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(flagProfessorOrStudent == 0 ){
                    System.out.println("permission-denied");
                }
                else{
                    myResource.addComment(arr[0],arr[1],arr[2],arr[3],arr[4]);
                    System.out.println("success");
                }

            }
            else if(order.toString().startsWith("search#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                ArrayList<String> resultOfSearch = new ArrayList<>();
                for(Library lib : libraries) {
                    for(ReadingResource resource : lib.getResources()) {
                        if(resource instanceof Thesis){
                            if (resource.getTitle().toLowerCase().contains(arr[0].toLowerCase()) || ((Thesis) resource).getStudentName().toLowerCase().contains(arr[0].toLowerCase()) || ((Thesis) resource).getProfessorName().toLowerCase().contains(arr[0].toLowerCase())) {
                                resultOfSearch.add(resource.getId());
                            }
                        }
                        else {
                            if (resource.getTitle().toLowerCase().contains(arr[0].toLowerCase()) || resource.getAuthor().toLowerCase().contains(arr[0].toLowerCase()) || resource.getPublisher().toLowerCase().contains(arr[0].toLowerCase())) {
                                resultOfSearch.add(resource.getId());
                            }
                        }
                    }
                }
                outputFotSearch(resultOfSearch);
            }
            else if(order.toString().startsWith("search-user#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                ArrayList<String> resultOfSearch = new ArrayList<>();
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                if(flagUser == 0 ){
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(it instanceof  Student){
                    System.out.println("permission-denied");
                }
                else{
                    for(Users user : users) {
                        if(user.getFirstName().toLowerCase().contains(arr[2].toLowerCase()) || user.getLastName().toLowerCase().contains(arr[2].toLowerCase())) {
                            resultOfSearch.add(user.getId());
                        }
                    }
                    outputFotSearch(resultOfSearch);
                }
            }
            else if(order.toString().startsWith("category-report#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int Book = 0 ;
                int Thesis = 0 ;
                int GhanjinehBook = 0 ;
                int SellingBook = 0 ;
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[3])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource instanceof Book){
                            if(resource.getCategory().getId().equals(arr[2]) || (!arr[2].equals("null")&& resource.getCategory().getFatherCategory().equals(arr[2]))) {
                                Book += resource.getNumberOfReadings();
                            }
                        }else if(resource instanceof Thesis){
                            if(resource.getCategory().getId().equals(arr[2]) || (!arr[2].equals("null")&& resource.getCategory().getFatherCategory().equals(arr[2]))) {
                                Thesis+= resource.getNumberOfReadings();
                            }
                        }else if(resource instanceof GhanjinehBook){
                            if(resource.getCategory().getId().equals(arr[2]) || (!arr[2].equals("null")&& resource.getCategory().getFatherCategory().equals(arr[2]))) {
                                GhanjinehBook++;
                            }
                        }else{
                            if(resource.getCategory().getId().equals(arr[2]) || (!arr[2].equals("null") && resource.getCategory().getFatherCategory().equals(arr[2]))) {
                                SellingBook += resource.getNumberOfReadings();
                            }
                        }

                    }
                }
                int flagCategory = 0 ;
                for(Category category : categories) {
                    if(category.getId().equals(arr[2])) {
                        flagCategory = 1;
                        break;
                    }
                }
                if(flagUser == 0 || flagLibrary == 0 || flagCategory == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!(it instanceof  Manager) || (it instanceof Manager && !((Manager)it).getIdLibrary().equals(arr[3]))) {
                    System.out.println("permission-denied");
                }
                else{
                    System.out.println(Book + " " + Thesis + " " + GhanjinehBook + " " + SellingBook);
                }
            }
            else if(order.toString().startsWith("library-report#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int AllBook = 0 ;
                int AllThesis = 0 ;
                int BorrowBook = 0 ;
                int BorrowThesis = 0 ;
                int AllGhanjinehBook = 0 ;
                int AllSellingBook = 0 ;
                if(myLib != null) {
                    for(ReadingResource resource : myLib.getResources()) {
                        if(resource instanceof Book){
                            AllBook += resource.getNumberOfReadings();
                            BorrowBook += ((Book)resource).getCteNumber() - resource.getNumberOfReadings();
                        }
                        else if(resource instanceof Thesis){
                            AllThesis += resource.getNumberOfReadings();
                            if(resource.getNumberOfReadings() == 0){
                                BorrowThesis+=1;
                            }

                        }
                        else if(resource instanceof GhanjinehBook){
                            AllGhanjinehBook += 1;
                        }
                        else{
                            AllSellingBook += resource.getNumberOfReadings();
                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!(it instanceof  Manager) || (it instanceof Manager && !((Manager)it).getIdLibrary().equals(arr[2]))) {
                    System.out.println("permission-denied");
                }
                else{
                    System.out.println(AllBook + " " + AllThesis + " " + BorrowBook + " " + BorrowThesis+ " " + AllGhanjinehBook + " " + AllSellingBook);
                }
            }
            else if(order.toString().startsWith("report-passed-deadline#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                TreeSet<String>resultOfSearch = new TreeSet<>();
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                    }
                    for(Borrower borrow : user.getBorrow()){
                        if(borrow.getIdLibrary().equals(arr[2])) {
                            if(user instanceof Student){
                                if(borrow.getBookOrThesis() == 1){
                                    if(calculateDateTimeDifference(arr[3],arr[4],borrow.getDateOfBorrow(),borrow.getClockOfBorrow()) - 10 *24 > 0){
                                        resultOfSearch.add(borrow.getIdBook());
                                    }
                                }
                                else if(borrow.getBookOrThesis() == -1){
                                    if(calculateDateTimeDifference(arr[3],arr[4],borrow.getDateOfBorrow(),borrow.getClockOfBorrow()) - 7 *24 > 0){
                                        resultOfSearch.add(borrow.getIdBook());
                                    }
                                }
                            }
                            else{
                                if(borrow.getBookOrThesis() == 1){
                                    if(calculateDateTimeDifference(arr[3],arr[4],borrow.getDateOfBorrow(),borrow.getClockOfBorrow()) - 14 *24 > 0){
                                        resultOfSearch.add(borrow.getIdBook());
                                    }
                                }
                                else if(borrow.getBookOrThesis() == -1){
                                    if(calculateDateTimeDifference(arr[3],arr[4],borrow.getDateOfBorrow(),borrow.getClockOfBorrow()) - 10 *24 > 0){
                                        resultOfSearch.add(borrow.getIdBook());
                                    }
                                }
                            }
                        }
                    }
                }
                int flagLibrary = 0;
                Library myLib = null;
                for (Library lib : libraries) {
                    if (lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                if(flagUser == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!(it instanceof  Manager) || (it instanceof Manager && !((Manager)it).getIdLibrary().equals(arr[2]))) {
                    System.out.println("permission-denied");
                }
                else{
                    if(resultOfSearch.isEmpty()){
                        System.out.println("none");
                    }
                    else{
                        int i = 0 ;
                        for(String str : resultOfSearch) {

                            if(i < resultOfSearch.size() - 1 ) {
                                System.out.print(str+"|");
                            }
                            else{
                                System.out.println(str);
                            }
                            i++;
                        }
                    }
                }
            }
            else if(order.toString().startsWith("report-penalties-sum#")) {
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                if(flagUser == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!arr[0].equals("admin") && !arr[1].equals("AdminPass")) {
                    System.out.println("permission-denied");
                }
                else{
                    System.out.println(penalty);
                }
            }
            else if(order.toString().startsWith("report-most-popular#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                long maxDayBook = -1 ;
                String maxIdBook = "";
                String maxIdThesis = "";
                long maxDayThesis = -1 ;
                int numBorrowedBooks = 0;
                int numBorrowedThesis = 0;
                if(myLib != null) {
                    for (ReadingResource resource : myLib.getResources()) {
                        if (resource instanceof Book) {
                            if (resource.getNumberOfDayBorrowed() > maxDayBook) {
                                maxDayBook = resource.getNumberOfDayBorrowed();
                                maxIdBook = resource.getId();
                                numBorrowedBooks = resource.getNumberBorrowed();
                            }

                        } else if (resource instanceof Thesis) {
                            if (resource.getNumberOfDayBorrowed() > maxDayThesis) {
                                maxDayThesis = resource.getNumberOfDayBorrowed();
                                maxIdThesis = resource.getId();
                                numBorrowedThesis = resource.getNumberBorrowed();
                            }
                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!(it instanceof  Manager) || (it instanceof Manager && !((Manager)it).getIdLibrary().equals(arr[2]))) {
                    System.out.println("permission-denied");
                }
                else{
                    if(maxDayBook != 0){
                        System.out.println(maxIdBook + " " + numBorrowedBooks + " " + maxDayBook);

                    }
                    else {
                        System.out.println("null");
                    }
                    if(maxDayThesis != 0){
                        System.out.println(maxIdThesis + " " + numBorrowedThesis+ " " +maxDayThesis);

                    }else {
                        System.out.println("null");
                    }
                }

            }
            else if(order.toString().startsWith("report-sell#")){
                order.delete(0, order.indexOf("#") + 1);
                String[] arr = order.toString().split("[|]");
                int flagUser = 0;
                Users it = null;
                for (Users user : users) {
                    if (user.getId().equals(arr[0])) {
                        flagUser = 1;
                        it = user;
                        break;
                    }
                }
                int flagLibrary = 0;
                Library myLib = null;
                for(Library lib : libraries) {
                    if(lib.getId().equals(arr[2])) {
                        flagLibrary = 1;
                        myLib = lib;
                        break;
                    }
                }
                int maxNumSell = -1;
                long maxTotalSell = -1 ;
                String maxIdSell = "";
                if(myLib != null) {
                    for (ReadingResource resource : myLib.getResources()) {
                        if (resource instanceof SellingBook) {
                            if (((SellingBook) resource).getNumberShellings() > maxNumSell) {
                                maxTotalSell = ((SellingBook) resource).getTotalShellings();
                                maxIdSell = resource.getId();
                                maxNumSell = ((SellingBook) resource).getNumberShellings();
                            }

                        }
                    }
                }
                if(flagUser == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(!it.getPassword().equals(arr[1])) {
                    System.out.println("invalid-pass");
                }
                else if(!(it instanceof  Manager) || (it instanceof Manager && !((Manager)it).getIdLibrary().equals(arr[2]))) {
                    System.out.println("permission-denied");
                }
                else{
                    System.out.println(myLib.getNumSell() + " " + myLib.getTotalSell() + "\n" + maxIdSell + " " + maxNumSell + " " + maxTotalSell);
                }

            }

        }
    }

    private static void outputFotSearch(ArrayList<String> resultOfSearch) {
        Collections.sort(resultOfSearch);
        if(!resultOfSearch.isEmpty()) {
            int i = 0 ;
            for(String str : resultOfSearch) {

                if(i < resultOfSearch.size() - 1 ) {
                    System.out.print(str+"|");
                }
                else{
                    System.out.println(str);
                }
                i++;
            }
        }
        else{
            System.out.println("not-found");
        }
    }
}
abstract class Person {
    private final String id;
    private final String password;
    public Person(String id, String password) {
        this.id = id;
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}
abstract class Users extends Person {
    private final String firstName;
    private final String lastName;
    private final String nationalityNumber;
    private final String yearOfBirth;
    private final String address;
    private boolean notReturn = false;
    protected int limitedNumber;
    private ArrayList<Borrower> borrows;

    public Users(String id, String password, String firstName, String lastName, String nationalityNumber, String yearOfBirth, String address) {
        super(id, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalityNumber = nationalityNumber;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        borrows = new ArrayList<>();
    }

    public boolean getIsNotReturn() {
        return notReturn;
    }
    public void setIsNotReturn(boolean notReturn) {
        this.notReturn = notReturn;
    }
    public ArrayList<Borrower> getBorrow() {
        return borrows;
    }
    public void addBorrow(Borrower borrow) {
        borrows.add(borrow);
    }
    public int getLimitedNumber() {
        return limitedNumber;
    }
    public void setLimitedNumber(int limitedNumber) {
        this.limitedNumber = limitedNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
class Admin extends Users {
    public Admin() {
        super("admin", "AdminPass", "", "", "", "" , "");
    }
}
class Student extends Users {
    public Student(String id, String password, String firstName, String lastName, String nationalityNumber, String yearOfBirth, String address) {
        super(id, password, firstName, lastName, nationalityNumber, yearOfBirth, address);
        limitedNumber = 3;
    }
}
class Staff extends Users {
    public Staff(String id, String password, String firstName, String lastName, String nationalityNumber, String yearOfBirth, String address) {
        super(id, password, firstName, lastName, nationalityNumber, yearOfBirth, address);
        limitedNumber = 5;
    }
}
class Manager extends Users {
    private final String idLibrary;

    public Manager(String id, String password, String firstName, String lastName, String nationalityNumber, String yearOfBirth, String address, String idLibrary) {
        super(id, password, firstName, lastName, nationalityNumber, yearOfBirth, address);
        this.idLibrary = idLibrary;
        limitedNumber = 5;
    }


    public String getIdLibrary() {
        return idLibrary;
    }
}
class Professor extends Users {
    public Professor(String id, String password, String firstName, String lastName, String nationalityNumber, String yearOfBirth, String address) {
        super(id, password, firstName, lastName, nationalityNumber, yearOfBirth, address);
        limitedNumber = 5;
    }

}
class Library{
    private final String id;
    private final String name;
    private final String yearOfEstablishment;
    private final int numberOfTable;
    private final String address;
    private ArrayList<ReadingResource> Resources;
    private long numSell = 0 ;
    private long totalSell = 0 ;
    public Library(String id, String name, String yearOfEstablishment, int numberOfTable, String address) {
        this.id = id;
        this.name = name;
        this.yearOfEstablishment = yearOfEstablishment;
        this.numberOfTable = numberOfTable;
        this.address = address;
        Resources = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public ArrayList<ReadingResource> getResources() {
        return Resources;
    }
    public void addReadingResource(ReadingResource readingResource) {
        Resources.add(readingResource);
    }

    public long getNumSell() {
        return numSell;
    }

    public void setNumSell(long numSell) {
        this.numSell = numSell;
    }

    public long getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(long totalSell) {
        this.totalSell = totalSell;
    }
}
class Category{
    private final String id;
    private final String name;
    private final String fatherCategory;
    public Category(String id, String name, String fatherCategory) {
        this.id = id;
        this.name = name;
        this.fatherCategory = fatherCategory;
    }
    public String getId() {
        return id;
    }

    public String getFatherCategory() {
        return fatherCategory;
    }
}
abstract class ReadingResource {
    private final String id;
    private final String title;
    private final String author;
    private final String publisher;
    private final String yearOfPublication;
    private int numberOfReadings;
    private final Category category;
    private final ArrayList<Commenter> comment;
    private long numberOfDayBorrowed = 0 ;
    private int numberBorrowed = 0;
    public ReadingResource(String id, String title, String author, String publisher, String yearOfPublication, int numberOfReadings, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.numberOfReadings = numberOfReadings;
        this.category = category;
        comment = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public int getNumberOfReadings() {
        return numberOfReadings;
    }
    public void setNumberOfReadings(int numberOfReadings) {
        this.numberOfReadings = numberOfReadings;
    }
    public void addComment(String idUser , String password , String idLibrary , String idResource , String comment) {
        this.comment.add(new Commenter(idUser, password, idLibrary, idResource, comment));
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Category getCategory() {
        return category;
    }

    public long getNumberOfDayBorrowed() {
        return numberOfDayBorrowed;
    }

    public void setNumberOfDayBorrowed(long numberOfDayBorrowed) {
        this.numberOfDayBorrowed = numberOfDayBorrowed;
    }

    public int getNumberBorrowed() {
        return numberBorrowed;
    }

    public void setNumberBorrowed(int numberBorrowed) {
        this.numberBorrowed = numberBorrowed;
    }
}
class Book extends ReadingResource {
    private final int cteNumber;
    public Book(String id, String title, String author, String publisher, String yearOfPublication, int numberOfReadings, Category category) {
        super(id, title, author, publisher, yearOfPublication, numberOfReadings, category);
        cteNumber = numberOfReadings;
    }
    public int getCteNumber() {
        return cteNumber;
    }
}
class Thesis extends ReadingResource {
    private final String studentName;
    private final String professorName;
    private final String yearOfDefence;

    public Thesis(String id, String title,String studentName, String professorName , String yearOfDefence , Category category) {
        super(id, title, "", "", "", 1, category);
        this.studentName = studentName;
        this.professorName = professorName;
        this.yearOfDefence = yearOfDefence;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getProfessorName() {
        return professorName;
    }
}
class GhanjinehBook extends ReadingResource {
    private final String donor;
    private String Date;
    private String Clock;
    public GhanjinehBook(String id, String title, String author, String publisher, String yearOfPublication, String donor , Category category) {
        super(id, title, author, publisher, yearOfPublication, 1, category);
        this.donor = donor;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public String getDate() {
        return Date;
    }
    public void setClock(String Clock) {
        this.Clock = Clock;
    }
    public String getClock() {
        return Clock;
    }
}
class SellingBook extends ReadingResource {
    private final long price;
    private final int discount;
    private int numberShellings = 0;
    private long totalShellings = 0;
    public SellingBook(String id, String title, String author, String publisher, String yearOfPublication, int numberOfReadings,long price ,int discount , Category category) {
        super(id, title, author, publisher, yearOfPublication, numberOfReadings, category);
        this.price = price;
        this.discount = discount;
    }

    public long getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public int getNumberShellings() {
        return numberShellings;
    }

    public void setNumberShellings(int numberShellings) {
        this.numberShellings = numberShellings;
    }

    public long getTotalShellings() {
        return totalShellings;
    }

    public void setTotalShellings(long totalShellings) {
        this.totalShellings = totalShellings;
    }
}

class Borrower extends Person{
    private final String idLibrary;
    private final String idBook;
    private int BookOrThesis  = 0 ;
    private final String dateOfBorrow;
    private final String clockOfBorrow;
    public Borrower(String idUser, String password, String idLibrary, String idBook, String dateOfBorrow, String clockOfBorrow, int BookOrThesis) {
        super(idUser,password);
        this.idLibrary = idLibrary;
        this.idBook = idBook;
        this.clockOfBorrow = clockOfBorrow;
        this.dateOfBorrow = dateOfBorrow;
        this.BookOrThesis = BookOrThesis;
    }
    public String getIdBook() {
        return idBook;
    }
    public String getDateOfBorrow() {
        return dateOfBorrow;
    }
    public String getClockOfBorrow() {
        return clockOfBorrow;
    }
    public String getIdLibrary() {
        return idLibrary;
    }

    public int getBookOrThesis() {
        return BookOrThesis;
    }
}
class Commenter extends Person {
    private final String idLibrary;
    private final String idBook;
    private final String comment;
    public Commenter(String idUser, String password, String idLibrary, String idBook, String comment) {
        super(idUser,password);
        this.idLibrary = idLibrary;
        this.idBook = idBook;
        this.comment = comment;
    }
}