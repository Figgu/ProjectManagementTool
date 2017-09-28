using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for ForgotPasswordWindow.xaml
    /// </summary>
    public partial class ForgotPasswordWindow : Window
    {
        private Kontext ktx;

        public ForgotPasswordWindow()
        {
            InitializeComponent();
            ktx = new Kontext();
        }

        private void BtnSendEmail_Click(object sender, RoutedEventArgs e)
        {
            User user = ktx.selectUser(txtEmail.Text);
            sendEmail(user);
            MessageBox.Show("Email with account details has been sent!");
            this.Close();
        }

        private void sendEmail(User user)
        {
            try
            {
                MailMessage mail = new MailMessage();
                SmtpClient SmtpServer = new SmtpClient("smtp.gmail.com");

                mail.From = new MailAddress("pmt2k17@gmail.com");
                mail.To.Add(user.Email);
                mail.Subject = "Forgot Password PMT";
                mail.Body = "Your account details: " + Environment.NewLine + "Username: " + user.Username + Environment.NewLine + "Password: " + user.Password;

                SmtpServer.Port = 587;
                SmtpServer.Credentials = new System.Net.NetworkCredential("pmt2k17@gmail.com", "projectmanagementtool");
                SmtpServer.EnableSsl = true;

                SmtpServer.Send(mail);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }
    }
}
