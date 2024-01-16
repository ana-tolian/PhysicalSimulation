package an.rozhnov.app.kernels;

public class ThreadWorker {

    protected int beginIndex;
    protected int endIndex;

    public void setBounds(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }
}
