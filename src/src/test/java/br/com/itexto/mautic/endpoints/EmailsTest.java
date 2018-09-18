package br.com.itexto.mautic.endpoints;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.itexto.mautic.MauticClient;
import br.com.itexto.mautic.MauticError;
import br.com.itexto.mautic.domain.Email;

public class EmailsTest extends BasicTest {

	@Test
	public void testSendToContact() throws MauticError {
		
		MauticClient client = getClient();
		
		Email email = new Email();
		email.setId(Long.parseLong(info("mautic.testSendToContact.emailId")));
		Long idContact = Long.parseLong(info("mautic.testSendToContact.contactId"));
		
		email.addParameter("from", "Kico");
		email.addParameter("name", "FDP!");
		
		assertTrue(client.emails().sendToContact(idContact, email));
		
	}

}
