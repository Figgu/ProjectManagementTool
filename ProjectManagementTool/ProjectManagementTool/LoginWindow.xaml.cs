using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Microsoft.VisualBasic;
using ProjectManagementTool.classes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for LoginWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        //opens main if checkcredentials returns a person
        private void LoginButton_Click(object sender, RoutedEventArgs e)
        {
            if(CheckCredentials(txtUsername.Text, txtPassword.Password) != null)
            {
                Main main = new Main();
                main.Show();
            }
            else
            {
                MessageBox.Show("invalid username/password");
            }
        }

        //Opens register window
        private void RegisterLabel_Click(object sender, RoutedEventArgs e)
        {
            RegisterWindow register = new RegisterWindow();
            register.Show();
        }

        //Opens forget pw window
        private void ForgotPwLabel_Click(object sender, RoutedEventArgs e)
        {
            ForgotPasswordWindow forgot = new ForgotPasswordWindow();
            forgot.Show(); 
        }

        //checks if the login data is valid via the kontext method
        private User CheckCredentials(String username, String password)
        {
            User retVal = null;
            Kontext ktx = new Kontext();
            if (!username.Equals("") && !password.Equals(""))
            {
                retVal = ktx.GetPerson(username, password);
            }
            return retVal;
        }
    }
}
