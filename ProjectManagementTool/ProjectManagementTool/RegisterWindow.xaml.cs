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
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for RegisterWindow.xaml
    /// </summary>
    public partial class RegisterWindow : Window
    {
        public RegisterWindow()
        {
            InitializeComponent();
        }

        //This method just goes back to the login screen
        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        //TODO Register user and check on textboxes
        private void btnRegister_Click(object sender, RoutedEventArgs e)
        {
            
            this.Close();
        }
    }
}
