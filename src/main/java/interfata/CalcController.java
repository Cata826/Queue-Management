package interfata;
import java.awt.event.*;
public class CalcController {
    private final CalcModel  m_model;
    private final CalcView  m_view;
    CalcController(CalcModel model, CalcView view) {
        m_model = model;
        m_view  = view;
        view.addMultiplyListener(new MultiplyListener());
    }
    class MultiplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int numar_clienti = Integer.parseInt(m_view.getUserInput());
                int numar_cozi = Integer.parseInt(m_view.getUserInput1());
                int timp_simulare = Integer.parseInt(m_view.getUserInput2());
                int timp_sosire_maxim = Integer.parseInt(m_view.getUserInput3());
                int timp_sosire_minim = Integer.parseInt(m_view.getUserInput4());
                int timp_servire_maxim = Integer.parseInt(m_view.getUserInput5());
                int timp_servire_minim = Integer.parseInt(m_view.getUserInput6());
                String output = m_model.generateClients2(numar_clienti, numar_cozi, timp_simulare, timp_sosire_maxim, timp_sosire_minim, timp_servire_maxim, timp_servire_minim);
                m_model.multiplyBy(numar_clienti, numar_cozi, timp_simulare, timp_sosire_maxim, timp_sosire_minim, timp_servire_maxim, timp_servire_minim);
                m_view.appendOutput(output);
            } catch (NumberFormatException ex) {
                m_view.showError("Invalid");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}