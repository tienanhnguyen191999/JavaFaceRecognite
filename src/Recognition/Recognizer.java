
package Recognition;

import Utils.Dao;
import capture.Capture;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_PLAIN;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.helper.opencv_core.CV_RGB;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;


public class Recognizer extends javax.swing.JFrame {
    private Recognizer.DaemonThread myThread = null;
    
    // Java CV
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("C:\\OpenCV_Source_code\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
    FaceRecognizer recognizer = LBPHFaceRecognizer.create(1,8,8,8,12);
    BytePointer mem = new BytePointer();
    RectVector detectedFaces = new RectVector();
    
    //Vars
    String root, fnamePerson, lnamePerson, dobPeron;
    int idPerson;
    
    // Utils
    Dao conn = new Dao();
    private Rect dadosFace;
    
    
    public Recognizer() {
        initComponents();
        recognizer.read("C:\\photos\\RecogniteWhiteWebcam\\classifierLBPH.yml");
        recognizer.setThreshold(70);
        startCamera();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        label_photo = new javax.swing.JLabel();
        label_dob = new javax.swing.JLabel();
        label_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Recognizer");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 102, 102));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Recognizer");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 462, 37));

        label_photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.add(label_photo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 440, 480));

        label_dob.setBackground(new java.awt.Color(0, 102, 204));
        label_dob.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_dob.setForeground(new java.awt.Color(255, 255, 255));
        label_dob.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_dob.setText("Dob");
        label_dob.setOpaque(true);
        jPanel1.add(label_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 440, 30));

        label_name.setBackground(new java.awt.Color(0, 102, 204));
        label_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_name.setForeground(new java.awt.Color(255, 255, 255));
        label_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_name.setText("FName-Lname");
        label_name.setOpaque(true);
        jPanel1.add(label_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 440, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Recognizer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_dob;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_photo;
    // End of variables declaration//GEN-END:variables
    class DaemonThread implements Runnable {
        protected volatile boolean runnable = false;
        @Override
        public void run(){
            synchronized (this) {
                while (runnable) {
                    try {
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();
                            Mat imageColor = new Mat();
                            imageColor = cameraImage;

                            Mat imageGray = new Mat();
                            cvtColor(imageColor, imageGray, COLOR_BGRA2GRAY);

                            RectVector detectedFaces = new RectVector();
                            cascade.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 0, new Size(150,150), new Size(500, 500));

                            for (int i = 0; i < detectedFaces.size(); i++) {
                                dadosFace = detectedFaces.get(i);
                                rectangle(imageColor, dadosFace, new Scalar(0, 255, 0, 0));
                                Mat face = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(face, face, new Size(160, 160));
                                
                                IntPointer rotulo = new IntPointer(1);
                                DoublePointer confidence = new DoublePointer(1);
                                recognizer.predict(face, rotulo, confidence);
                                int predict = rotulo.get(0);
                                String name = "";
                                if(predict == -1){
                                    rectangle(cameraImage, dadosFace, new Scalar(0, 0, 255, 3), 3, 0, 0);
                                    label_name.setText("");
                                    label_photo.setText("");
                                }else{
                                    rectangle(cameraImage, dadosFace, new Scalar(0, 255, 0, 3), 3, 0, 0);
                                    System.out.println(confidence.get(0));
                                    idPerson = predict;
                                    try {
                                        rec();
                                    } catch (Exception ex) {
                                        Logger.getLogger(Recognizer.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;

                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 90, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Salve a Foto");
                                    this.wait();
                                }
                            }
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao iniciar camera (IOEx)\n" + ex);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao iniciar camera (Interrupted)\n" + ex);
                    }
                }
            }
        }

    }
    
    private void rec()throws  Exception{
        // Recognizer face in database
//        SwingWorker worker = new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
                String SQL = "select * from user where id = " + idPerson;
                conn.executeSQL(SQL);
                while ( conn.rs.next()){
                    String label = conn.rs.getString("fname");
                    opencv_imgproc.putText(cameraImage,label, new Point(dadosFace.x(),dadosFace.y()) , FONT_HERSHEY_PLAIN, 1.0 , new Scalar(0,0,255,3),2,opencv_imgproc.LINE_AA,false);
                    System.out.println("IN");
                    label_name.setText(conn.rs.getString("fname") + " - " + conn.rs.getString("lname"));
                    label_dob.setText(conn.rs.getString("dob"));
                    System.out.println("Person: " + conn.rs.getString("id"));
                    
//                    Array ident = conn.rs.getArray("fname");
//                    String person[] = (String[]) ident.getArray();
//                    System.out.println("ONCE RUN: \n\n\n");
//                    for (int i = 0 ;i < person.length; i++){
//                       // System.out.println(person[i]);
//                    }
                }
//                return null;
//            }
//        };
        //worker.execute();
    }
   
    
    private void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        dispose();
    }
    
    public void startCamera(){
        webSource = new VideoCapture(0);
        myThread = new Recognizer.DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
    }
}
