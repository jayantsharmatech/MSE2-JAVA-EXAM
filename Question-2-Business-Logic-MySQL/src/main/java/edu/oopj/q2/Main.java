package edu.oopj.q2;

import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length>0&&args[0].equals("--demo")){demo();return;}
        StudentService service=new StudentService(new StudentRepository());
        System.out.println("Student manager connected to MySQL. Type help for commands; exit to quit.");
        Scanner in=new Scanner(System.in);
        while(true){System.out.print("student> ");String line=in.nextLine().trim();if(line.equalsIgnoreCase("exit"))break;
            try{handle(service,line,in);}catch(Exception e){System.out.println("Error: "+e.getMessage());}
        }
    }
    private static void handle(StudentService s,String line,Scanner in)throws SQLException{
        if(line.equalsIgnoreCase("help")){System.out.println("add | list | merit | shared | improvement | find <id|email|course text> | delete <id> | exit");return;}
        if(line.equals("list")){s.all().forEach(Main::print);return;}
        if(line.equals("merit")){int n=1;for(Student x:s.meritList())System.out.println((n++)+". "+summary(x));return;}
        if(line.equals("shared")){s.sharedSkills().forEach((k,v)->System.out.println(k+": "+v));return;}
        if(line.equals("improvement")){s.needsProgrammingImprovement().forEach(Main::print);return;}
        if(line.startsWith("find ")){s.search(line.substring(5)).forEach(Main::print);return;}
        if(line.startsWith("delete ")){System.out.println(s.delete(line.substring(7))?"Deleted":"Student not found");return;}
        if(line.equals("add")){System.out.print("ID: ");String id=in.nextLine();System.out.print("Name: ");String name=in.nextLine();
            System.out.print("Email: ");String email=in.nextLine();System.out.print("Course: ");String course=in.nextLine();
            System.out.print("Programming score: ");double p=Double.parseDouble(in.nextLine());System.out.print("Mathematics score: ");double m=Double.parseDouble(in.nextLine());
            System.out.print("Technical skills (comma separated): ");List<String> skills=Arrays.stream(in.nextLine().split(",")).map(String::trim).filter(x->!x.isEmpty()).toList();
            s.add(new Student(id,name,email,course,p,m,skills));System.out.println("Student added");return;}
        System.out.println("Unknown command. Type help.");
    }
    private static void print(Student s){System.out.println(summary(s)+" | stronger="+s.strongerSubject()+" | "+s.technicalSkills());}
    private static String summary(Student s){return String.format(Locale.ROOT,"%s | %s | %.2f | %s",s.id(),s.name(),s.average(),s.category());}
    private static void demo(){
        List<Student> students=List.of(
                new Student("S1001","Asha Rao","asha@example.com","BCA",88,76,List.of("Java","SQL")),
                new Student("S1002","Ravi Kumar","ravi@example.com","BSc CS",42,81,List.of("Java","Python")),
                new Student("S1003","Meera Das","meera@example.com","BCA",67,69,List.of("SQL","Python")));
        System.out.println("QUESTION 2 - STUDENT PROFILE AND SKILL MANAGEMENT");
        System.out.println("Demo run using Java collections (no database required)\n");
        System.out.println("1-3. STUDENT AVERAGES, PERFORMANCE CATEGORY AND STRONGER SUBJECT");
        for(Student s:students) System.out.printf(Locale.ROOT,"%-6s %-12s average=%5.2f  category=%-19s stronger=%s%n",
                s.id(),s.name(),s.average(),s.category(),s.strongerSubject());

        System.out.println("\n4. SEARCH BY ID, EMAIL AND COURSE");
        showSearch("ID S1002", students.stream().filter(s->s.id().equalsIgnoreCase("S1002")).toList());
        showSearch("email meera@example.com", students.stream().filter(s->s.email().equalsIgnoreCase("meera@example.com")).toList());
        showSearch("course BCA", students.stream().filter(s->s.courseName().equalsIgnoreCase("BCA")).toList());

        System.out.println("\n5. MERIT LIST (AVERAGE SCORE, DESCENDING)");
        List<Student> merit=students.stream().sorted(Comparator.comparingDouble(Student::average).reversed()).toList();
        for(int i=0;i<merit.size();i++)System.out.printf(Locale.ROOT,"%d. %s - %.2f%n",i+1,merit.get(i).name(),merit.get(i).average());

        System.out.println("\n6. TECHNICAL SKILLS SHARED BY STUDENTS");
        Map<String,List<String>> owners=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for(Student s:students)for(String skill:s.technicalSkills())owners.computeIfAbsent(skill,k->new ArrayList<>()).add(s.name());
        owners.forEach((skill,names)->{if(names.size()>1)System.out.println(skill+" -> "+String.join(", ",names));});

        System.out.println("\n7. PROGRAMMING BELOW 50 AND MATHEMATICS AT LEAST 75");
        students.stream().filter(s->s.programmingScore()<50&&s.mathematicsScore()>=75).forEach(s->
                System.out.printf(Locale.ROOT,"%s (%s): programming=%.2f, mathematics=%.2f%n",s.name(),s.id(),s.programmingScore(),s.mathematicsScore()));

        System.out.println("\n8. VALIDATION CHECKS");
        Map<String,Student> uniqueIds=new LinkedHashMap<>();
        for(Student s:students)uniqueIds.put(s.id().toLowerCase(Locale.ROOT),s);
        if(uniqueIds.putIfAbsent("s1001",students.get(1))!=null)System.out.println("Duplicate ID S1001 rejected");
        try{new Student("S9999","Test Student","test@example.com","BCA",101,50,List.of());}
        catch(IllegalArgumentException e){System.out.println("Invalid score 101 rejected: "+e.getMessage());}
        try{new Student("S9998","Test Student","test2@example.com","BCA",50,-1,List.of());}
        catch(IllegalArgumentException e){System.out.println("Invalid score -1 rejected: "+e.getMessage());}
        System.out.println("\nRun with MySQL: configure DB_USERNAME/DB_PASSWORD, create sql/schema.sql, then use mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java.");
    }
    private static void showSearch(String label,List<Student> results){
        System.out.print("Search by "+label+": ");
        System.out.println(results.isEmpty()?"no match":results.stream().map(s->s.name()+" ("+s.id()+")").reduce((a,b)->a+", "+b).orElse("no match"));
    }

}
