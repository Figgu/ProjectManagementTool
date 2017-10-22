using ProjectManagementTool.classes;
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
    /// Interaction logic for Main.xaml
    /// </summary>
    public partial class Main : Window
    {
        private Kontext ktx = new Kontext();
        private User currentUser;

        public Main(User user)
        {
            InitializeComponent();
            this.currentUser = user;
            LoadGUI();

            GenerateListItems(20);
        }

        private void GenerateListItems(int numberOfItems)
        {
            for (int i=0; i<numberOfItems; i++)
            {
                ListBoxItem item = new ListBoxItem
                {
                    Content = "Item " + i,
                    FontSize = 30,
                    Height = 50
                };


                projectList.Items.Add(item);
            }
        }

        private void LoadGUI()
        {
            lblProfile.Inlines.Clear();
            lblProfile.Inlines.Add(currentUser.Username);
        }

        private void Logout(object sender, RoutedEventArgs e)
        {
            LoginWindow login = new LoginWindow();
            login.Show();
            this.Close();
        }
    }
}
