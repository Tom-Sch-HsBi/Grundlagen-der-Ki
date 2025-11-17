import aima.core.search.csp.*;

import java.util.*;

public class CSP02BT_Search {

    public static void main(String[] args) {

        // 5 Häuser
        int N = 5;

        // Kategorien
        String[] colors  = {"Rot","Gruen","Blau","Gelb","Weiss"};
        String[] nations = {"Brite","Schwede","Daene","Norweger","Deutscher"};
        String[] drinks  = {"Tee","Kaffee","Milch","Bier","Wasser"};
        String[] smokes  = {"PallMall","Dunhill","Blend","BlueMaster","Prince"};
        String[] pets    = {"Hunde","Voegel","Katzen","Pferde","Fische"};

        // Variablen: z.B. Farbe1..5, Nation1..5 ...
        List<String> vars = new ArrayList<>();
        Map<String,List<String>> domains = new HashMap<>();

        String[] cats = {"Farbe","Nation","Getraenk","Zigarrette","Tier"};

        for(String cat: cats){
            for(int i=1;i<=N;i++){
                String var = cat+i;
                vars.add(var);
                switch(cat){
                    case "Farbe": domains.put(var,new ArrayList<>(Arrays.asList(colors))); break;
                    case "Nation": domains.put(var,new ArrayList<>(Arrays.asList(nations))); break;
                    case "Getraenk": domains.put(var,new ArrayList<>(Arrays.asList(drinks))); break;
                    case "Zigarrette": domains.put(var,new ArrayList<>(Arrays.asList(smokes))); break;
                    case "Tier": domains.put(var,new ArrayList<>(Arrays.asList(pets))); break;
                }
            }
        }

        // CSP erstellen
        CSP csp = new CSP();
        for(String v: vars){
            csp.addVariable(v);
        }

        // Alle Constraints implementieren
        List<ConstraintCustom> constraints = new ArrayList<>();

        // AllDifferent pro Kategorie
        for(String cat: cats){
            List<String> catVars = new ArrayList<>();
            for(int i=1;i<=N;i++) catVars.add(cat+i);
            constraints.add(new AllDiff(catVars));
        }

        // Unary Constraints
        constraints.add(new Unary("Nation1","Norweger"));
        constraints.add(new Unary("Getraenk3","Milch"));

        // Gleichheitsconstraints (zwei Variablen gleichzeitig)
        constraints.add(new Eq("Nation","Brite","Farbe","Rot"));
        constraints.add(new Eq("Nation","Schwede","Tier","Hunde"));
        constraints.add(new Eq("Nation","Daene","Getraenk","Tee"));
        constraints.add(new Eq("Farbe","Gruen","Getraenk","Kaffee"));
        constraints.add(new Eq("Zigarrette","PallMall","Tier","Voegel"));
        constraints.add(new Eq("Farbe","Gelb","Zigarrette","Dunhill"));
        constraints.add(new Eq("Zigarrette","BlueMaster","Getraenk","Bier"));
        constraints.add(new Eq("Nation","Deutscher","Zigarrette","Prince"));

        // Nachbarschaftsconstraints
        constraints.add(new LeftOf("Farbe","Gruen","Farbe","Weiss"));
        constraints.add(new NextTo("Zigarrette","Blend","Tier","Katzen"));
        constraints.add(new NextTo("Tier","Pferde","Zigarrette","Dunhill"));
        constraints.add(new NextTo("Zigarrette","Blend","Getraenk","Wasser"));
        constraints.add(new NextTo("Nation","Norweger","Farbe","Blau"));

        // Solver Backtracking
        Assignment solution = backtrack(new HashMap<>(), vars, domains, constraints);
        if(solution != null){
            for(String v: vars){
                System.out.println(v + " = " + solution.getAssignment(v));
            }
        }else{
            System.out.println("Keine Lösung gefunden");
        }
    }

    // Backtracking
    static Assignment backtrack(Map<String,String> assignment, List<String> vars,
                                Map<String,List<String>> domains,
                                List<ConstraintCustom> constraints){
        if(assignment.size() == vars.size()){
            Assignment finalAssign = new Assignment();
            for(String v: assignment.keySet()) finalAssign.setAssignment(v,assignment.get(v));
            return finalAssign;
        }

        String var = null;
        for(String v: vars){
            if(!assignment.containsKey(v)){
                var = v; break;
            }
        }

        for(String val: domains.get(var)){
            assignment.put(var,val);
            if(isConsistent(var,assignment,constraints)){
                Assignment result = backtrack(assignment,vars,domains,constraints);
                if(result!=null) return result;
            }
            assignment.remove(var);
        }
        return null;
    }

    static boolean isConsistent(String var, Map<String,String> assignment, List<ConstraintCustom> constraints){
        for(ConstraintCustom c: constraints){
            if(!c.isSatisfied(assignment)) return false;
        }
        return true;
    }

    // ----------------- Constraint-Implementierungen -----------------

    interface ConstraintCustom{
        boolean isSatisfied(Map<String,String> assignment);
    }

    static class Unary implements ConstraintCustom{
        String var, value;
        public Unary(String var,String value){ this.var=var; this.value=value; }
        public boolean isSatisfied(Map<String,String> assignment){
            return !assignment.containsKey(var) || assignment.get(var).equals(value);
        }
    }

    static class Eq implements ConstraintCustom{
        String c1,v1,c2,v2;
        public Eq(String c1,String v1,String c2,String v2){ this.c1=c1; this.v1=v1; this.c2=c2; this.v2=v2; }
        public boolean isSatisfied(Map<String,String> assignment){
            for(int i=1;i<=5;i++){
                String var1=c1+i;
                String var2=c2+i;
                if(assignment.containsKey(var1) && assignment.containsKey(var2)){
                    String val1=assignment.get(var1);
                    String val2=assignment.get(var2);
                    if(!((val1.equals(v1) && val2.equals(v2)) || (!val1.equals(v1) && !val2.equals(v2)))) return false;
                }
            }
            return true;
        }
    }

    static class AllDiff implements ConstraintCustom{
        List<String> vars;
        public AllDiff(List<String> vars){ this.vars=vars; }
        public boolean isSatisfied(Map<String,String> assignment){
            Set<String> seen = new HashSet<>();
            for(String v: vars){
                if(assignment.containsKey(v)){
                    if(seen.contains(assignment.get(v))) return false;
                    seen.add(assignment.get(v));
                }
            }
            return true;
        }
    }

    static class NextTo implements ConstraintCustom{
        String c1,v1,c2,v2;
        public NextTo(String c1,String v1,String c2,String v2){ this.c1=c1; this.v1=v1; this.c2=c2; this.v2=v2; }
        public boolean isSatisfied(Map<String,String> assignment){
            for(int i=1;i<=5;i++){
                String var1=c1+i;
                if(assignment.containsKey(var1) && assignment.get(var1).equals(v1)){
                    boolean ok=false;
                    if(i>1){
                        String varLeft=c2+(i-1);
                        if(assignment.containsKey(varLeft) && assignment.get(varLeft).equals(v2)) ok=true;
                    }
                    if(i<5){
                        String varRight=c2+(i+1);
                        if(assignment.containsKey(varRight) && assignment.get(varRight).equals(v2)) ok=true;
                    }
                    if(!ok) return false;
                }
            }
            return true;
        }
    }

    static class LeftOf implements ConstraintCustom{
        String c1,v1,c2,v2;
        public LeftOf(String c1,String v1,String c2,String v2){ this.c1=c1; this.v1=v1; this.c2=c2; this.v2=v2; }
        public boolean isSatisfied(Map<String,String> assignment){
            for(int i=1;i<5;i++){
                String var1=c1+i;
                String var2=c2+(i+1);
                if(assignment.containsKey(var1) && assignment.containsKey(var2)){
                    if(assignment.get(var1).equals(v1) && !assignment.get(var2).equals(v2)) return false;
                }
            }
            return true;
        }
    }
}
