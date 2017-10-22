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
using System.Data;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for LoginWindow.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        private Kontext ktx;

        public LoginWindow()
        {
            InitializeComponent();
            ktx = new Kontext();
  
        }

        //opens main if checkcredentials returns a person
        private void LoginButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                User user = CheckCredentials(txtUsername.Text, txtPassword.Password);
                if (user != null)
                {
                    Main main = new Main(user);
                    main.Show();
                    this.Close();
                }
                else
                {
                    MessageBox.Show("invalid username/password");
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Username or password not correct!");
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
            if (!username.Equals("") && !password.Equals(""))
            {
                retVal = ktx.selectUser(username, password);
            }
            return retVal;
        }
    }
}
